package com.advantest.demeter.core.database.project.task.table

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/12/4
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskTableColumnDBTable()(implicit val db: Database) extends DBTable  {
  override protected type TableRowData = ProjectTaskTableColumnDBTableRow
  override protected val table: TableQuery[ProjectTaskTableColumnDBTableSchema] = TableQuery[ProjectTaskTableColumnDBTableSchema]
  createTableIfNotExists()

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
