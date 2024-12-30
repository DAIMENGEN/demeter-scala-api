package com.advantest.demeter.database.table.project.task.attribute

import com.advantest.demeter.database.{DBFieldType, DBTableSchemaAbstract}
import com.advantest.demeter.json.JsonObject
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/1
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskAttributeDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectTaskAttributeDBTableRow](tag, "PROJECT_TASK_ATTRIBUTE_DB_TABLE") {

  def taskAttributeName: Rep[String] = column[String]("TASK_ATTRIBUTE_NAME", O.Length(255))

  def taskAttributeType: Rep[DBFieldType] = column[DBFieldType]("TASK_ATTRIBUTE_TYPE", O.Length(255))

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  def properties: Rep[Option[JsonObject]] = column[Option[JsonObject]]("PROPERTIES", O.Default(None))

  def order: Rep[Int] = column[Int]("ORDER")

  override def * : ProvenShape[ProjectTaskAttributeDBTableRow] = (
    id,
    taskAttributeName,
    taskAttributeType,
    projectId,
    properties,
    order,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskAttributeDBTableRow.apply _).tupled, ProjectTaskAttributeDBTableRow.unapply)
}
