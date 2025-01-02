package com.advantest.demeter.database.table.department

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBTableSchemaAbstract, DBTextValue, DBVarcharValue}
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final class DepartmentDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[DepartmentDBTableRow](tag, "DEPARTMENT_DB_TABLE") {

  def name: Rep[DBVarcharValue] = column[DBVarcharValue]("NAME", O.SqlType("VARCHAR"), O.Length(255))

  def description: Rep[Option[DBTextValue]] = column[Option[DBTextValue]]("DESCRIPTION", O.SqlType("TEXT"))

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
