package com.advantest.demeter.core.database.project.task.field

import com.advantest.demeter.utils.database.{DBFieldType, DBTableSchema}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/12/1
 * Author: mengen.dai@outlook.com
 */
class ProjectTaskFieldDBTableSchema(tag: Tag) extends Table[ProjectTaskFieldDBTableRow](tag, "PROJECT_TASK_FIELD_DB_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def fieldName: Rep[String] = column[String]("FIELD_NAME")

  def fieldType: Rep[DBFieldType] = column[DBFieldType]("FIELD_TYPE")

  def isSystemField: Rep[Boolean] = column[Boolean]("IS_SYSTEM_FIELD", O.Default(true))

  def projectId: Rep[Option[Long]] = column[Option[Long]]("PROJECT_ID", O.Default(None))

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[ProjectTaskFieldDBTableRow] = (
    id,
    fieldName,
    fieldType,
    isSystemField,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskFieldDBTableRow.apply _).tupled, ProjectTaskFieldDBTableRow.unapply)
}
