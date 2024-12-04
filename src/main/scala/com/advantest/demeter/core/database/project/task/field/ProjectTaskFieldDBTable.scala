package com.advantest.demeter.core.database.project.task.field

import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.utils.database._
import com.advantest.demeter.utils.snowflake.SnowflakeIdUtil
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskFieldDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectTaskFieldDBTableRow
  override protected val table: TableQuery[ProjectTaskFieldDBTableSchema] = TableQuery[ProjectTaskFieldDBTableSchema]
  createTableIfNotExists()
  initSystemFields()

  private def initSystemFields(): Future[Seq[TableRowData]] = {
    querySystemField().flatMap(rows => {
      if (rows.isEmpty) {
        val fields: Seq[(String, DBFieldType)] = Seq(
          ("id", LongType),
          ("taskName", StringType),
          ("description", StringType),
          ("taskType", IntType),
          ("taskStatus", IntType),
          ("taskRule", StringType),
          ("startDateTime", DateTimeType),
          ("endDateTime", DateTimeType),
          ("parentId", LongType),
          ("projectId", LongType),
          ("order", IntType),
          ("creatorId", LongType),
          ("updaterId", LongType),
          ("createDateTime", DateTimeType),
          ("updateDateTime", DateTimeType),
        )
        val systemFields = fields.map {
          case (fieldName, fieldType) => ProjectTaskFieldDBTableRow(
            id = SnowflakeIdUtil.nextId(),
            fieldName = fieldName,
            fieldType = fieldType,
            isSystemField = true,
            projectId = None,
            creatorId = 1,
            updaterId = 1,
          )
        }
        batchInsert(systemFields)
      } else Future.successful(Seq.empty)
    })
  }

  def queryByFieldName(fieldName: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.fieldName === fieldName).result.headOption
    db.run(select)
  }

  def queryByFieldNameLike(fieldNamePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.fieldName like s"%$fieldNamePattern%").result
    db.run(select)
  }

  def queryByFieldType(fieldType: DBFieldType): Future[Seq[TableRowData]] = {
    val select = table.filter(_.fieldType === fieldType).result
    db.run(select)
  }

  def querySystemField(): Future[Seq[TableRowData]] = {
    val select = table.filter(_.isSystemField).result
    db.run(select)
  }

  def queryDynamicField(): Future[Seq[TableRowData]] = {
    val select = table.filter(!_.isSystemField).result
    db.run(select)
  }

  def queryDynamicFiledByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }
}
