package com.advantest.demeter.core.database.project.task.field

import com.advantest.demeter.core.database.project.task.{ProjectTaskTableRow, ProjectTaskTableSchema}
import com.advantest.demeter.core.database.project.{ProjectTableRow, ProjectTableSchema}
import com.advantest.demeter.utils.database.{DBFieldType, DBTableSchema}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskFieldTableSchema(tag: Tag) extends Table[ProjectTaskFieldTableRow](tag, "PROJECT_TASK_FIELD_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def fieldName: Rep[String] = column[String]("FIELD_NAME")

  def fieldValue: Rep[String] = column[String]("FIELD_VALUE")

  def fieldType: Rep[DBFieldType] = column[DBFieldType]("FIELD_TYPE")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def order: Rep[Option[Int]] = column[Option[Int]]("ORDER")

  def projectTaskId: Rep[Long] = column[Long]("PROJECT_TASK_ID")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  def projectIdFk: ForeignKeyQuery[ProjectTableSchema, ProjectTableRow] = foreignKey("PROJECT_FK", projectId, TableQuery[ProjectTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  def projectTaskFk: ForeignKeyQuery[ProjectTaskTableSchema, ProjectTaskTableRow] = foreignKey("PROJECT_TASK_FK", projectTaskId, TableQuery[ProjectTaskTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  override def * : ProvenShape[ProjectTaskFieldTableRow] = (
    id,
    fieldName,
    fieldValue,
    fieldType,
    description,
    order,
    projectTaskId,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskFieldTableRow.apply _).tupled, ProjectTaskFieldTableRow.unapply)
}
