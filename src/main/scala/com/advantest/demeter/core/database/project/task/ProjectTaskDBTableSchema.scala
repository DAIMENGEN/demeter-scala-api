package com.advantest.demeter.core.database.project.task

import com.advantest.demeter.utils.database.DBTableSchemaAbstract
import slick.jdbc.MySQLProfile.api._
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
