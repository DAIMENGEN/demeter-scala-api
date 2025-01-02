package com.advantest.demeter.database.table.employee

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBTable, DBVarcharValue}

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
    val select = table.filter(_.employeeName === DBVarcharValue(employeeName)).result.headOption
    db.run(select)
  }

  def queryByEmployeeNameLike(employeeNamePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeName.asColumnOf[String] like s"%$employeeNamePattern%").result
    db.run(select)
  }

  def queryByAccount(account: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.account === DBVarcharValue(account)).result.headOption
    db.run(select)
  }

  def queryByAccountLike(accountPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.account.asColumnOf[String] like s"%$accountPattern%").result
    db.run(select)
  }

  def queryByEmail(email: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.email === DBVarcharValue(email)).result.headOption
    db.run(select)
  }

  def queryByEmailLike(emailPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.email.asColumnOf[String] like s"%$emailPattern%").result
    db.run(select)
  }

  def queryByPhone(phone: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.phone === DBVarcharValue(phone)).result.headOption
    db.run(select)
  }

  def queryByPhoneLike(phonePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.phone.asColumnOf[Option[String]] like s"%$phonePattern%").result
    db.run(select)
  }
}
