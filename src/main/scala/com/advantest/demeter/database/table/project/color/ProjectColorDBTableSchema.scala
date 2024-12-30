package com.advantest.demeter.database.table.project.color

import com.advantest.demeter.database.DBTableSchemaAbstract
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final class ProjectColorDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectColorDBTableRow](tag, "PROJECT_COLOR_DB_TABLE") {

  def value: Rep[String] = column[String]("VALUE")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def fieldName: Rep[String] = column[String]("FIELD_NAME")

  def fieldValue: Rep[String] = column[String]("FIELD_VALUE")

  def order: Rep[Option[Int]] = column[Option[Int]]("ORDER")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

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
