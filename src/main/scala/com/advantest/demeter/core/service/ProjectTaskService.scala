package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.core.database.project.task.ProjectTaskDBTable
import com.advantest.demeter.core.database.project.task.attribute.ProjectTaskAttributeDBTable
import com.advantest.demeter.core.database.project.task.value.{ProjectTaskAttributeValueDBTableRow, ProjectTaskBooleanTypeAttributeValueDBTable => BooleanTypeValueDBTable, ProjectTaskDateTypeAttributeValueDBTable => DateTypeValueDBTable, ProjectTaskDatetimeTypeAttributeValueDBTable => DatetimeTypeValueDBTable, ProjectTaskDoubleTypeAttributeValueDBTable => DoubleTypeValueDBTable, ProjectTaskFloatTypeAttributeValueDBTable => FloatTypeValueDBTable, ProjectTaskIntTypeAttributeValueDBTable => IntTypeValueDBTable, ProjectTaskJsonTypeAttributeValueDBTable => JsonTypeValueDBTable, ProjectTaskLongTypeAttributeValueDBTable => LongTypeValueDBTable, ProjectTaskLongtextTypeAttributeValueDBTable => LongtextTypeValueDBTable, ProjectTaskMediumtextTypeAttributeValueDBTable => MediumtextTypeValueDBTable, ProjectTaskStringTypeAttributeValueDBTable => StringTypeValueDBTable, ProjectTaskTextTypeAttributeValueDBTable => TextTypeValueDBTable}
import com.advantest.demeter.core.http.payload.{ProjectTaskAttributePayload, ProjectTaskAttributeValuePayload, ProjectTaskPayload}
import com.advantest.demeter.utils.database._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
case class ProjectTaskService()(implicit val db: Database) extends Service {
  private val dbTable: ProjectTaskDBTable = ProjectTaskDBTable()
  private val attributeDBTable: ProjectTaskAttributeDBTable = ProjectTaskAttributeDBTable()
  private val intTypeValueDBTable: IntTypeValueDBTable = IntTypeValueDBTable()
  private val dateTypeValueDBTable: DateTypeValueDBTable = DateTypeValueDBTable()
  private val jsonTypeValueDBTable: JsonTypeValueDBTable = JsonTypeValueDBTable()
  private val longTypeValueDBTable: LongTypeValueDBTable = LongTypeValueDBTable()
  private val floatTypeValueDBTable: FloatTypeValueDBTable = FloatTypeValueDBTable()
  private val doubleTypeValueDBTable: DoubleTypeValueDBTable = DoubleTypeValueDBTable()
  private val stringTypeValueDBTable: StringTypeValueDBTable = StringTypeValueDBTable()
  private val booleanTypeValueDBTable: BooleanTypeValueDBTable = BooleanTypeValueDBTable()
  private val datetimeTypeValueDBTable: DatetimeTypeValueDBTable = DatetimeTypeValueDBTable()
  private val textTypeValueDBTable: TextTypeValueDBTable = TextTypeValueDBTable()
  private val longtextTypeValueDBTable: LongtextTypeValueDBTable = LongtextTypeValueDBTable()
  private val mediumtextTypeValueDBTable: MediumtextTypeValueDBTable = MediumtextTypeValueDBTable()

  private def buildInsertTaskAttributeValueRowsAction(taskAttributeValueRows: Seq[ProjectTaskAttributeValueDBTableRow]) = {
    DBIO.sequence(taskAttributeValueRows.map { taskAttributeValueRow =>
      taskAttributeValueRow.taskAttributeValue match {
        case DBIntValue(_) => intTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBLongValue(_) => longTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBFloatValue(_) => floatTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBDoubleValue(_) => doubleTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBStringValue(_) => stringTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBTextValue(_) => textTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBMediumtextValue(_) => mediumtextTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBLongtextValue(_) => longtextTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBBooleanValue(_) => booleanTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBJsonValue(_) => jsonTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBDateValue(_) => dateTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBDateTimeValue(_) => datetimeTypeValueDBTable.tableQuery += taskAttributeValueRow
      }
    })
  }

  def getTasksByProjectId(projectId: Long): Future[Seq[ProjectTaskPayload]] = {
    val taskRowsFuture = dbTable.queryByProjectId(projectId)
    val taskAttributeRowsFuture = attributeDBTable.queryByProjectId(projectId)
    val taskIntAttributeValueRowsFuture = Future.sequence(Seq(
      intTypeValueDBTable.queryByProjectId(projectId),
      dateTypeValueDBTable.queryByProjectId(projectId),
      jsonTypeValueDBTable.queryByProjectId(projectId),
      longTypeValueDBTable.queryByProjectId(projectId),
      floatTypeValueDBTable.queryByProjectId(projectId),
      doubleTypeValueDBTable.queryByProjectId(projectId),
      stringTypeValueDBTable.queryByProjectId(projectId),
      booleanTypeValueDBTable.queryByProjectId(projectId),
      datetimeTypeValueDBTable.queryByProjectId(projectId),
      textTypeValueDBTable.queryByProjectId(projectId),
      longtextTypeValueDBTable.queryByProjectId(projectId),
      mediumtextTypeValueDBTable.queryByProjectId(projectId)
    )).map(_.flatten)
    for {
      taskRows <- taskRowsFuture
      taskAttributeRows <- taskAttributeRowsFuture
      taskIntAttributeValueRows <- taskIntAttributeValueRowsFuture
    } yield {
      val taskAttributeEntities = taskAttributeRows.map(_.toPayload)
      val taskIntAttributeValueEntitiesMap = taskIntAttributeValueRows.map(_.toPayload).groupBy(_.taskId)
      taskRows.map { taskRow =>
        ProjectTaskPayload(
          id = taskRow.id,
          taskName = taskRow.taskName,
          taskAttributes = taskAttributeEntities,
          taskAttributeValues = taskIntAttributeValueEntitiesMap.getOrElse(taskRow.id, Seq.empty),
          order = taskRow.order
        )
      }
    }
  }

  def getTaskAttributesByProjectId(projectId: Long): Future[Seq[ProjectTaskAttributePayload]] = {
    attributeDBTable.queryByProjectId(projectId).map(_.map(_.toPayload))
  }

  def createTask(employeeId: Long, projectId: Long, task: ProjectTaskPayload): Future[ProjectTaskPayload] = {
    createTasks(employeeId, projectId, Seq(task)).map {
      case Seq(task) => task
      case _ => throw new RuntimeException("create task failed")
    }
  }

  def createTasks(employeeId: Long, projectId: Long, tasks: Seq[ProjectTaskPayload]): Future[Seq[ProjectTaskPayload]] = {
    val optionData = Some(Map("projectId" -> projectId))
    val taskRow = tasks.map(task => ProjectTaskPayload.create(employeeId, task, optionData))
    val taskRowIds = taskRow.map(_.id)
    val taskAttributeValueRows = tasks.flatMap(task => task.taskAttributeValues.map(taskAttributeValue => ProjectTaskAttributeValuePayload.create(employeeId, taskAttributeValue, optionData)))
    val insertTaskRows = dbTable.tableQuery ++= taskRow
    val insertTaskAttributeValueRows = buildInsertTaskAttributeValueRowsAction(taskAttributeValueRows)
    val selectTaskRows = dbTable.tableQuery.filter(_.id.inSet(taskRowIds)).result
    val selectTaskAttributes = attributeDBTable.tableQuery.filter(_.projectId === projectId).result
    val selectTaskAttributeValueRows = DBIO.sequence(Seq(
      intTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      dateTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      jsonTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      longTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      floatTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      doubleTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      stringTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      booleanTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      datetimeTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      textTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      longtextTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
      mediumtextTypeValueDBTable.tableQuery.filter(_.id.inSet(taskRowIds)).result,
    ))
    val transaction = (insertTaskRows andThen insertTaskAttributeValueRows andThen (selectTaskRows zip selectTaskAttributes zip selectTaskAttributeValueRows)).transactionally
    db.run(transaction).map {
      case ((taskRows, taskAttributeRows), taskAttributeValueRows) =>
        taskRows.map { taskRow =>
          ProjectTaskPayload(
            id = taskRow.id,
            taskName = taskRow.taskName,
            taskAttributes = taskAttributeRows.map(_.toPayload),
            taskAttributeValues = taskAttributeValueRows.flatten.filter(_.id == taskRow.id).map(_.toPayload),
          )
        }
    }
  }

  def createTaskAttribute(employeeId: Long, projectId: Long, attribute: ProjectTaskAttributePayload): Future[ProjectTaskAttributePayload] = {
    val optionData = Some(Map("projectId" -> projectId))
    val tableRowData = ProjectTaskAttributePayload.create(employeeId, attribute, optionData)
    attributeDBTable.insert(tableRowData).map(_.toPayload)
  }

  def createTaskAttributes(employeeId: Long, projectId: Long, attributes: Seq[ProjectTaskAttributePayload]): Future[Seq[ProjectTaskAttributePayload]] = {
    val optionData = Some(Map("projectId" -> projectId))
    val tableRows = attributes.map(attribute => ProjectTaskAttributePayload.create(employeeId, attribute, optionData))
    attributeDBTable.batchInsert(tableRows).map(_.map(_.toPayload))
  }
}
