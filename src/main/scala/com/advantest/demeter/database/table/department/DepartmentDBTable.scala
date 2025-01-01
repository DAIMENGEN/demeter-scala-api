package com.advantest.demeter.database.table.department

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.DBTable

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class DepartmentDBTable()(implicit val db: Database) extends DBTable {

  override protected type TableRowData = DepartmentDBTableRow
  override protected val table: TableQuery[DepartmentDBTableSchema] = TableQuery[DepartmentDBTableSchema]
  createTableIfNotExists()

  def queryByName(name: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.name === name).result.headOption
    db.run(select)
  }

  def queryByNameLike(namePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.name like s"%$namePattern%").result
    db.run(select)
  }

  def queryByDescriptionLike(descriptionPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.description like s"%$descriptionPattern%").result
    db.run(select)
  }
}
