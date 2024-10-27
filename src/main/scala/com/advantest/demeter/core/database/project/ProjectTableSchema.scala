package com.advantest.demeter.core.database.project

import com.advantest.demeter.core.constant.project.ProjectStatus
import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final class ProjectTableSchema(tag: Tag) extends Table[ProjectTableRow](tag, "PROJECT_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def name: Rep[String] = column[String]("NAME")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def startDateTime: Rep[LocalDateTime] = column[LocalDateTime]("START_DATE_TIME")

  def endDateTime: Rep[Option[LocalDateTime]] = column[Option[LocalDateTime]]("END_DATE_TIME")

  def status: Rep[ProjectStatus] = column[ProjectStatus]("STATUS")

  def version: Rep[Option[Int]] = column[Option[Int]]("VERSION")

  def order: Rep[Option[Int]] = column[Option[Int]]("ORDER")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[ProjectTableRow] = (
    id,
    name,
    description,
    startDateTime,
    endDateTime,
    status,
    version,
    order,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTableRow.apply _).tupled, ProjectTableRow.unapply)
}