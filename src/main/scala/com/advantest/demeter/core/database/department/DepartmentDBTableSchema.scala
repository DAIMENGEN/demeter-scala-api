package com.advantest.demeter.core.database.department

import com.advantest.demeter.utils.database.DBTableSchemaAbstract
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final class DepartmentDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[DepartmentDBTableRow](tag, "DEPARTMENT_DB_TABLE") {

  def name: Rep[String] = column[String]("NAME")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

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
