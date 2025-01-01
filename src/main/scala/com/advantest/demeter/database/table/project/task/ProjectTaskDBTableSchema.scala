package com.advantest.demeter.database.table.project.task

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.DBTableSchemaAbstract
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectTaskDBTableRow](tag, "PROJECT_TASK_DB_TABLE") {

  def taskName: Rep[String] = column[String]("TASK_NAME", O.Length(255))

  def order: Rep[Option[Int]] = column[Option[Int]]("ORDER")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  override def * : ProvenShape[ProjectTaskDBTableRow] = (
    id,
    taskName,
    order,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskDBTableRow.apply _).tupled, ProjectTaskDBTableRow.unapply)
}
