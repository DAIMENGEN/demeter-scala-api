package com.advantest.demeter.core.database.department

import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final class DepartmentDBTableSchema(tag: Tag) extends Table[DepartmentDBTableRow](tag, "DEPARTMENT_DB_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def name: Rep[String] = column[String]("NAME")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[DepartmentDBTableRow] = (
    id,
    name,
    description,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((DepartmentDBTableRow.apply _).tupled, DepartmentDBTableRow.unapply)
}
