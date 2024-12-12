package com.advantest.demeter.core.database.project.task.table

import com.advantest.demeter.utils.database.DBTable
import com.advantest.demeter.utils.json.JsonObject
import slick.jdbc.MySQLProfile.api._
import spray.json._

import scala.concurrent.Future

/**
 * Create on 2024/12/4
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskTableColumnDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectTaskTableColumnDBTableRow
  override protected val table: TableQuery[ProjectTaskTableColumnDBTableSchema] = TableQuery[ProjectTaskTableColumnDBTableSchema]
  createTableIfNotExists()

//  initDefaultTableColumns()

  private def initDefaultTableColumns(): Future[Seq[TableRowData]] = {
    val taskNameColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "TaskName",
      dataIndex = "taskName",
      employeeId = 1,
      order = 1,
      minWidth = Some(200),
      filedProps = Some(JsonObject(Map(
        "placeholder" -> JsString("This field is required.")
      ))),
      formItemProps = Some(JsonObject(Map(
        "rules" -> JsArray(Vector.apply(JsObject(Map(
          "required" -> JsBoolean(true),
          "message" -> JsString("This field is required.")
        ))))
      )))
    )

    val taskOwnerColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "TaskOwner",
      dataIndex = "taskOwner",
      employeeId = 1,
      order = 2,
      valueType = Some("select"),
      minWidth = Some(180),
      filedProps = Some(JsonObject(Map(
        "showSearch" -> JsBoolean(true),
        "filterOption" -> JsBoolean(false),
      )))
    )

    val taskAssignerColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "TaskAssigner",
      dataIndex = "taskAssigner",
      employeeId = 1,
      order = 3,
      valueType = Some("select"),
      minWidth = Some(180),
      filedProps = Some(JsonObject(Map(
        "showSearch" -> JsBoolean(true),
        "filterOption" -> JsBoolean(false),
      )))
    )

    val taskTypeColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "TaskType",
      dataIndex = "taskType",
      employeeId = 1,
      order = 4,
      valueType = Some("select"),
      minWidth = Some(180),
      filedProps = Some(JsonObject(Map(
        "options" -> JsArray(),
        "showSearch" -> JsBoolean(false),
      )))
    )

    val taskStatusColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "TaskStatus",
      dataIndex = "taskStatus",
      employeeId = 1,
      order = 5,
      valueType = Some("select"),
      minWidth = Some(180),
      filedProps = Some(JsonObject(Map(
        "options" -> JsArray(),
        "showSearch" -> JsBoolean(false),
      )))
    )

    val formItemProps = JsonObject(Map(
      "rules" -> JsArray(Vector.apply(JsObject(Map(
        "required" -> JsBoolean(true),
        "message" -> JsString("This field is required.")
      )), JsObject(Map(
        "pattern" -> JsString("^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"),
        "message" -> JsString("Please enter a valid date format, e.g. 1954-01-01.")
      ))))
    ))

    val startDateTimeColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "StartDate",
      dataIndex = "startDateTime",
      employeeId = 1,
      order = 6,
      minWidth = Some(150),
      formItemProps = Some(formItemProps)
    )

    val endDateTimeColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "EndDate",
      dataIndex = "endDateTime",
      employeeId = 1,
      order = 7,
      minWidth = Some(150),
      formItemProps = Some(formItemProps)
    )

    val descriptionColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "Description",
      dataIndex = "description",
      employeeId = 1,
      order = 8,
      valueType = Some("textarea"),
      minWidth = Some(300),
      filedProps = Some(JsonObject(Map(
        "rows" -> JsNumber(1)
      )))
    )

    val taskRuleColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "TaskRule",
      dataIndex = "taskRule",
      employeeId = 1,
      order = 9,
      minWidth = Some(200)
    )

    val orderColumn = ProjectTaskTableColumnDBTableRow.createColumnRow(
      title = "Order",
      dataIndex = "order",
      employeeId = 1,
      order = 10,
      minWidth = Some(100)
    )

    batchInsert(Seq.apply(
      taskNameColumn,
      taskOwnerColumn,
      taskAssignerColumn,
      taskTypeColumn,
      taskStatusColumn,
      startDateTimeColumn,
      endDateTimeColumn,
      descriptionColumn,
      taskRuleColumn,
      orderColumn,
    ))
  }

  def queryByKey(key: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.key === key).result.headOption
    db.run(select)
  }

  def queryByKeyLike(keyPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.key like s"%$keyPattern%").result
    db.run(select)
  }

  def queryByTitle(title: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.title === title).result
    db.run(select)
  }

  def queryByTitleLike(titlePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.title like s"%$titlePattern%").result
    db.run(select)
  }

  def queryByDataIndex(dataIndex: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.dataIndex === dataIndex).result
    db.run(select)
  }

  def queryByDataIndexLike(dataIndexPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.dataIndex like s"%$dataIndexPattern%").result
    db.run(select)
  }

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }
}
