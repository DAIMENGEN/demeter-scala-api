package com.advantest.demeter.database.table.team

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBTableSchema, DBTableSchemaAbstract, DBTextValue, DBVarcharValue}
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final class TeamDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[TeamDBTableRow](tag, "TEAM_DB_TABLE") with DBTableSchema {

  def name: Rep[DBVarcharValue] = column[DBVarcharValue]("NAME", O.SqlType("VARCHAR"), O.Length(255))

  def description: Rep[Option[DBTextValue]] = column[Option[DBTextValue]]("DESCRIPTION", O.SqlType("TEXT"), O.Default(None))

  override def * : ProvenShape[TeamDBTableRow] = (
    id,
    name,
    description,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((TeamDBTableRow.apply _).tupled, TeamDBTableRow.unapply)
}
