package com.advantest.demeter.core.database.employee

import com.advantest.demeter.utils.database.DBTableSchemaAbstract
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final class EmployeeDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[EmployeeDBTableRow](tag, "EMPLOYEE_DB_TABLE") {

  def account: Rep[String] = column[String]("ACCOUNT")

  def password: Rep[String] = column[String]("PASSWORD")

  def employeeName: Rep[String] = column[String]("EMPLOYEE_NAME")

  def email: Rep[String] = column[String]("EMAIL")

  def phone: Rep[Option[String]] = column[Option[String]]("PHONE")

  def isActive: Rep[Boolean] = column[Boolean]("IS_ACTIVE")

  override def * : ProvenShape[EmployeeDBTableRow] = (
    id,
    account,
    password,
    employeeName,
    email,
    phone,
    isActive,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((EmployeeDBTableRow.apply _).tupled, EmployeeDBTableRow.unapply)
}
