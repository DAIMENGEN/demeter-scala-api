package com.advantest.demeter.core.database.project.task.table

import com.advantest.demeter.utils.database.DBTableSchema
import com.advantest.demeter.utils.json.JsonObject
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/12/4
 * Author: mengen.dai@outlook.com
 */
class ProjectTaskTableColumnDBTableSchema(tag: Tag) extends Table[ProjectTaskTableColumnDBTableRow](tag, "PROJECT_TASK_TABLE_COLUMN_DB_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def key: Rep[String] = column[String]("KEY")

  def title: Rep[String] = column[String]("TITLE")

  def dataIndex: Rep[String] = column[String]("DATA_INDEX")

  def valueType: Rep[String] = column[String]("VALUE_TYPE")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION", O.Default(None))

  def filedProps: Rep[Option[JsonObject]] = column[Option[JsonObject]]("FIELD_PROPS", O.Default(None))

  def formItemProps: Rep[Option[JsonObject]] = column[Option[JsonObject]]("FORM_ITEM_PROPS", O.Default(None))

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[ProjectTaskTableColumnDBTableRow] = (
    id,
    key,
    title,
    dataIndex,
    valueType,
    description,
    filedProps,
    formItemProps,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskTableColumnDBTableRow.apply _).tupled, ProjectTaskTableColumnDBTableRow.unapply)
}
