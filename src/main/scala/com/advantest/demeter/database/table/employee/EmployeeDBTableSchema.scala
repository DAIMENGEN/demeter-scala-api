package com.advantest.demeter.database.table.employee

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBBooleanValue, DBTableSchemaAbstract, DBVarcharValue}
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final class EmployeeDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[EmployeeDBTableRow](tag, "EMPLOYEE_DB_TABLE") {

  def account: Rep[DBVarcharValue] = column[DBVarcharValue]("ACCOUNT", O.SqlType("VARCHAR"), O.Length(255))

  def password: Rep[DBVarcharValue] = column[DBVarcharValue]("PASSWORD", O.SqlType("VARCHAR"), O.Length(255))

  def employeeName: Rep[DBVarcharValue] = column[DBVarcharValue]("EMPLOYEE_NAME", O.SqlType("VARCHAR"), O.Length(255))

  def email: Rep[DBVarcharValue] = column[DBVarcharValue]("EMAIL", O.SqlType("VARCHAR"), O.Length(255))

  def phone: Rep[Option[DBVarcharValue]] = column[Option[DBVarcharValue]]("PHONE", O.SqlType("VARCHAR"), O.Length(15), O.Default(None))

  def isActive: Rep[DBBooleanValue] = column[DBBooleanValue]("IS_ACTIVE")

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
