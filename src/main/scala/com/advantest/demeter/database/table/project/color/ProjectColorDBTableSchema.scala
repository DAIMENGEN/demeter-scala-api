package com.advantest.demeter.database.table.project.color

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database._
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final class ProjectColorDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectColorDBTableRow](tag, "PROJECT_COLOR_DB_TABLE") {

  def value: Rep[DBVarcharValue] = column[DBVarcharValue]("VALUE", O.SqlType("VARCHAR"), O.Length(255))

  def description: Rep[Option[DBTextValue]] = column[Option[DBTextValue]]("DESCRIPTION", O.SqlType("TEXT"))

  def fieldName: Rep[DBVarcharValue] = column[DBVarcharValue]("FIELD_NAME", O.SqlType("VARCHAR"), O.Length(255))

  def fieldValue: Rep[DBVarcharValue] = column[DBVarcharValue]("FIELD_VALUE", O.SqlType("VARCHAR"), O.Length(255))

  def order: Rep[Option[DBIntValue]] = column[Option[DBIntValue]]("ORDER", O.Default(None))

  def projectId: Rep[DBLongValue] = column[DBLongValue]("PROJECT_ID")

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
