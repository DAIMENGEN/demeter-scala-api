package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.core.database.project.task.ProjectTaskDBTable
import com.advantest.demeter.core.database.project.task.attribute.ProjectTaskAttributeDBTable
import com.advantest.demeter.core.database.project.task.value.{ProjectTaskBooleanTypeAttributeValueDBTable => BooleanTypeValueDBTable, ProjectTaskDateTypeAttributeValueDBTable => DateTypeValueDBTable, ProjectTaskDatetimeTypeAttributeValueDBTable => DatetimeTypeValueDBTable, ProjectTaskDoubleTypeAttributeValueDBTable => DoubleTypeValueDBTable, ProjectTaskFloatTypeAttributeValueDBTable => FloatTypeValueDBTable, ProjectTaskIntTypeAttributeValueDBTable => IntTypeValueDBTable, ProjectTaskJsonTypeAttributeValueDBTable => JsonTypeValueDBTable, ProjectTaskLongTypeAttributeValueDBTable => LongTypeValueDBTable, ProjectTaskLongtextTypeAttributeValueDBTable => LongtextTypeValueDBTable, ProjectTaskMediumtextTypeAttributeValueDBTable => MediumtextTypeValueDBTable, ProjectTaskStringTypeAttributeValueDBTable => StringTypeValueDBTable, ProjectTaskTextTypeAttributeValueDBTable => TextTypeValueDBTable}
import com.advantest.demeter.core.entity.project.task.ProjectTaskEntity
import com.advantest.demeter.core.entity.project.task.attribute.ProjectTaskAttributeEntity
import com.advantest.demeter.core.entity.project.task.value.ProjectTaskAttributeValueEntity
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

  def getTasksByProjectId(projectId: Long): Future[Seq[ProjectTaskEntity]] = {
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
      val taskAttributeEntities = taskAttributeRows.map(_.toEntity)
      val taskIntAttributeValueEntitiesMap = taskIntAttributeValueRows.map(_.toEntity).groupBy(_.taskId)
      taskRows.map { taskRow =>
        ProjectTaskEntity(
          id = taskRow.id,
          taskName = taskRow.taskName,
          taskAttributes = taskAttributeEntities,
          taskAttributeValues = taskIntAttributeValueEntitiesMap.getOrElse(taskRow.id, Seq.empty),
          order = taskRow.order
        )
      }
    }
  }

  def getTaskAttributesByProjectId(projectId: Long): Future[Seq[ProjectTaskAttributeEntity]] = {
    attributeDBTable.queryByProjectId(projectId).map(_.map(_.toEntity))
  }

  def createTask(employeeId: Long, projectId: Long, task: ProjectTaskEntity): Future[ProjectTaskEntity] = {
    val optionData = Some(Map("projectId" -> projectId))
    val taskRow = ProjectTaskEntity.create(employeeId, task, optionData)
    val taskAttributeValueRows = task.taskAttributeValues.map(taskAttributeValue => ProjectTaskAttributeValueEntity.create(employeeId, taskAttributeValue, optionData))
    val insertTaskRow = dbTable.tableQuery += taskRow
    val insertTaskAttributeValueRows = DBIO.sequence(taskAttributeValueRows.map { taskAttributeValueRow =>
      val taskAttributeValue = taskAttributeValueRow.taskAttributeValue
      taskAttributeValue match {
        case DBIntTypeValue(_) => intTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBLongTypeValue(_) => longTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBFloatTypeValue(_) => floatTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBDoubleTypeValue(_) => doubleTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBStringTypeValue(_) => stringTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBTextTypeValue(_) => textTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBMediumtextTypeValue(_) => mediumtextTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBLongtextTypeValue(_) => longtextTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBBooleanTypeValue(_) => booleanTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBJsonTypeValue(_) => jsonTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBDateTypeValue(_) => dateTypeValueDBTable.tableQuery += taskAttributeValueRow
        case DBDateTimeTypeValue(_) => datetimeTypeValueDBTable.tableQuery += taskAttributeValueRow
      }
    })
    val selectTaskRow = dbTable.tableQuery.filter(_.id === taskRow.id).result.headOption
    val selectTaskAttributes = attributeDBTable.tableQuery.filter(_.projectId === projectId).result
    val selectTaskAttributeValueRows = DBIO.sequence(Seq(
      intTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      dateTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      jsonTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      longTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      floatTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      doubleTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      stringTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      booleanTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      datetimeTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      textTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      longtextTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result,
      mediumtextTypeValueDBTable.tableQuery.filter(_.id === taskRow.id).result
    ))
    val transaction = (insertTaskRow andThen insertTaskAttributeValueRows andThen (selectTaskRow zip selectTaskAttributes zip selectTaskAttributeValueRows)).transactionally
    db.run(transaction).map {
      case ((Some(taskRow), taskAttributeRows), taskAttributeValueRows) =>
        ProjectTaskEntity(
          id = taskRow.id,
          taskName = taskRow.taskName,
          taskAttributes = taskAttributeRows.map(_.toEntity),
          taskAttributeValues = taskAttributeValueRows.flatten.map(_.toEntity),
        )
      case _ => throw new RuntimeException("Failed to create task")
    }
  }

  def createTaskAttribute(employeeId: Long, projectId: Long, attribute: ProjectTaskAttributeEntity): Future[ProjectTaskAttributeEntity] = {
    val optionData = Some(Map("projectId" -> projectId))
    val tableRowData = ProjectTaskAttributeEntity.create(employeeId, attribute, optionData)
    attributeDBTable.insert(tableRowData).map(_.toEntity)
  }

  def createTaskAttributes(employeeId: Long, projectId: Long, attributes: Seq[ProjectTaskAttributeEntity]): Future[Seq[ProjectTaskAttributeEntity]] = {
    val optionData = Some(Map("projectId" -> projectId))
    val tableRows = attributes.map(attribute => ProjectTaskAttributeEntity.create(employeeId, attribute, optionData))
    attributeDBTable.batchInsert(tableRows).map(_.map(_.toEntity))
  }
}
