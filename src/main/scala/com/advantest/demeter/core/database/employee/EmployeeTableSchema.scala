package com.advantest.demeter.core.database.employee

import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final class EmployeeTableSchema(tag: Tag) extends Table[EmployeeTableRow](tag, "EMPLOYEE_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def account: Rep[String] = column[String]("ACCOUNT")

  def password: Rep[String] = column[String]("PASSWORD")

  def employeeName: Rep[String] = column[String]("EMPLOYEE_NAME")

  def email: Rep[String] = column[String]("EMAIL")

  def phone: Rep[Option[String]] = column[Option[String]]("PHONE")

  def isActive: Rep[Boolean] = column[Boolean]("IS_ACTIVE")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[EmployeeTableRow] = (
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
  ) <> ((EmployeeTableRow.apply _).tupled, EmployeeTableRow.unapply)
}
