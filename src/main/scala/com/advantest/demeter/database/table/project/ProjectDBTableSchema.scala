package com.advantest.demeter.database.table.project

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database._
import slick.lifted.ProvenShape

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final class ProjectDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectDBTableRow](tag, "PROJECT_DB_TABLE") {

  def projectName: Rep[DBVarcharValue] = column[DBVarcharValue]("PROJECT_NAME", O.SqlType("VARCHAR"), O.Length(255))

  def description: Rep[Option[DBTextValue]] = column[Option[DBTextValue]]("DESCRIPTION", O.SqlType("TEXT"), O.Default(None))

  def startDateTime: Rep[DBDateValue] = column[DBDateValue]("START_DATE_TIME", O.SqlType("VARCHAR"), O.Length(10))

  def endDateTime: Rep[Option[DBDateValue]] = column[Option[DBDateValue]]("END_DATE_TIME", O.SqlType("VARCHAR"), O.Length(10), O.Default(None))

  def projectStatus: Rep[DBIntValue] = column[DBIntValue]("PROJECT_STATUS")

  def version: Rep[Option[DBIntValue]] = column[Option[DBIntValue]]("VERSION", O.Default(None))

  def order: Rep[Option[DBIntValue]] = column[Option[DBIntValue]]("ORDER", O.Default(None))

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
