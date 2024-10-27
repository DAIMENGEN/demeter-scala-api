package com.advantest.demeter.core.database.project.task.field

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskFieldTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectTaskFieldTableRow
  override protected val table: TableQuery[ProjectTaskFieldTableSchema] = TableQuery[ProjectTaskFieldTableSchema]
  createTableIfNotExists()

  def queryByFieldName(fieldName: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.fieldName === fieldName).result.headOption
    db.run(select)
  }

  def queryByFieldNameLike(fieldNamePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.fieldName like s"%$fieldNamePattern%").result
    db.run(select)
  }

  def queryByDescriptionLike(descriptionPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.description like s"%$descriptionPattern%").result
    db.run(select)
  }

  def queryByTaskId(askId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectTaskId === askId).result
    db.run(select)
  }

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }
}
