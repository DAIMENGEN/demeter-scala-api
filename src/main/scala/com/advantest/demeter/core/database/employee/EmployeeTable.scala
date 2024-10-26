package com.advantest.demeter.core.database.employee

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeTable(implicit val db: Database) extends DBTable {

  override protected type TableRowData = EmployeeTableRow
  override protected val table: TableQuery[EmployeeTableSchema] = TableQuery[EmployeeTableSchema]
  createTableIfNotExists()

  def queryByAccount(account: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.account === account).result.headOption
    db.run(select)
  }

  def queryByEmail(email: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.email === email).result.headOption
    db.run(select)
  }

  def queryByPhone(phone: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.phone === phone).result.headOption
    db.run(select)
  }
}
