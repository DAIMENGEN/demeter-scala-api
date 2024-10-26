package com.advantest.demeter.core.database

import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
class DepartmentTableSchema(tag: Tag) extends Table[DepartmentTableRow](tag, "DEPARTMENT_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def name: Rep[String] = column[String]("NAME")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[DepartmentTableRow] = (
    id,
    name,
    description,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((DepartmentTableRow.apply _).tupled, DepartmentTableRow.unapply)
}
