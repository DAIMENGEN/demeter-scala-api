package com.advantest.demeter.database.table.project

import com.advantest.demeter.constant.project.ProjectStatus
import com.advantest.demeter.database.DBTableSchemaAbstract
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDate

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final class ProjectDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectDBTableRow](tag, "PROJECT_DB_TABLE") {

  def projectName: Rep[String] = column[String]("PROJECT_NAME")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def startDateTime: Rep[LocalDate] = column[LocalDate]("START_DATE_TIME")

  def endDateTime: Rep[Option[LocalDate]] = column[Option[LocalDate]]("END_DATE_TIME")

  def projectStatus: Rep[ProjectStatus] = column[ProjectStatus]("PROJECT_STATUS")

  def version: Rep[Option[Int]] = column[Option[Int]]("VERSION")

  def order: Rep[Option[Int]] = column[Option[Int]]("ORDER")

  override def * : ProvenShape[ProjectDBTableRow] = (
    id,
    projectName,
    description,
    startDateTime,
    endDateTime,
    projectStatus,
    version,
    order,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectDBTableRow.apply _).tupled, ProjectDBTableRow.unapply)
}
