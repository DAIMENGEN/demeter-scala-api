package com.advantest.demeter.core.database

import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
class TeamTableSchema(tag: Tag) extends Table[TeamTableRow](tag, "TEAM_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def name: Rep[String] = column[String]("NAME")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def departmentId: Rep[Option[Long]] = column[Option[Long]]("DEPARTMENT_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[TeamTableRow] = (
    id,
    name,
    description,
    departmentId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((TeamTableRow.apply _).tupled, TeamTableRow.unapply)
}
