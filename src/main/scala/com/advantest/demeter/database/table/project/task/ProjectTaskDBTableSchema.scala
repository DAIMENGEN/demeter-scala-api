package com.advantest.demeter.database.table.project.task

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBIntValue, DBLongValue, DBTableSchemaAbstract, DBVarcharValue}
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectTaskDBTableRow](tag, "PROJECT_TASK_DB_TABLE") {

  def taskName: Rep[DBVarcharValue] = column[DBVarcharValue]("TASK_NAME", O.SqlType("VARCHAR"), O.Length(255))

  def parentTaskId: Rep[Option[DBLongValue]] = column[Option[DBLongValue]]("PARENT_TASK_ID", O.Default(None))

  def projectId: Rep[DBLongValue] = column[DBLongValue]("PROJECT_ID")

  override def * : ProvenShape[ProjectTaskDBTableRow] = (
    id,
    taskName,
    parentTaskId,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskDBTableRow.apply _).tupled, ProjectTaskDBTableRow.unapply)
}
