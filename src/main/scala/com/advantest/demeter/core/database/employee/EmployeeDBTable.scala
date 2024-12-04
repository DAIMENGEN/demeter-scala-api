package com.advantest.demeter.core.database.employee

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeDBTable()(implicit val db: Database) extends DBTable {

  override protected type TableRowData = EmployeeDBTableRow
  override protected val table: TableQuery[EmployeeDBTableSchema] = TableQuery[EmployeeDBTableSchema]
  createTableIfNotExists()

  def queryByEmployeeName(employeeName: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.employeeName === employeeName).result.headOption
    db.run(select)
  }

  def queryByEmployeeNameLike(employeeNamePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeName like s"%$employeeNamePattern%").result
    db.run(select)
  }

  def queryByAccount(account: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.account === account).result.headOption
    db.run(select)
  }

  def queryByAccountLike(accountPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.account like s"%$accountPattern%").result
    db.run(select)
  }

  def queryByEmail(email: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.email === email).result.headOption
    db.run(select)
  }

  def queryByEmailLike(emailPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.email like s"%$emailPattern%").result
    db.run(select)
  }

  def queryByPhone(phone: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.phone === phone).result.headOption
    db.run(select)
  }

  def queryByPhoneLike(phonePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.phone like s"%$phonePattern%").result
    db.run(select)
  }
}
