package com.advantest.demeter.core.database.project.color

import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final class ProjectColorDBTableSchema(tag: Tag) extends Table[ProjectColorDBTableRow](tag, "PROJECT_COLOR_DB_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def value: Rep[String] = column[String]("VALUE")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def fieldName: Rep[String] = column[String]("FIELD_NAME")

  def fieldValue: Rep[String] = column[String]("FIELD_VALUE")

  def order: Rep[Option[Int]] = column[Option[Int]]("ORDER")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[ProjectColorDBTableRow] = (
    id,
    value,
    description,
    fieldName,
    fieldValue,
    order,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectColorDBTableRow.apply _).tupled, ProjectColorDBTableRow.unapply)
}
