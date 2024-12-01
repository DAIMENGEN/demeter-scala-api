package com.advantest.demeter.core.database.project.task

import com.advantest.demeter.core.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskTableSchema(tag: Tag) extends Table[ProjectTaskTableRow](tag, "PROJECT_TASK_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def taskName: Rep[String] = column[String]("TASK_NAME")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def taskType: Rep[ProjectTaskType] = column[ProjectTaskType]("TASK_TYPE")

  def taskStatus: Rep[ProjectTaskStatus] = column[ProjectTaskStatus]("TASK_STATUS")

  def taskRule: Rep[Option[String]] = column[Option[String]]("TASK_RULE")

  def startDateTime: Rep[LocalDateTime] = column[LocalDateTime]("START_DATE_TIME")

  def endDateTime: Rep[Option[LocalDateTime]] = column[Option[LocalDateTime]]("END_DATE_TIME")

  def parentId: Rep[Option[Long]] = column[Option[Long]]("PARENT_ID")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  def order: Rep[Option[Int]] = column[Option[Int]]("ORDER")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[ProjectTaskTableRow] = (
    id,
    taskName,
    description,
    taskType,
    taskStatus,
    taskRule,
    startDateTime,
    endDateTime,
    parentId,
    projectId,
    order,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskTableRow.apply _).tupled, ProjectTaskTableRow.unapply)
}
