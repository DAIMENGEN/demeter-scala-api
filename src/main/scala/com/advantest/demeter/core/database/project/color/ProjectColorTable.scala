package com.advantest.demeter.core.database.project.color

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final case class ProjectColorTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectColorTableRow
  override protected val table: TableQuery[ProjectColorTableSchema] = TableQuery[ProjectColorTableSchema]
  createTableIfNotExists()


  def queryByValue(value: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.value === value).result
    db.run(select)
  }

  def queryByFieldName(fieldName: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.fieldName === fieldName).result
    db.run(select)
  }

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }

  def queryByDescriptionLike(descriptionPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.description like s"%$descriptionPattern%").result
    db.run(select)
  }
}
