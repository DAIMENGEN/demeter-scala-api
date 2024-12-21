package com.advantest.demeter.core.database.project.task.attribute

import com.advantest.demeter.utils.database.{DBFieldType, DBTableSchemaAbstract}
import com.advantest.demeter.utils.json.JsonObject
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/1
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskAttributeDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectTaskAttributeDBTableRow](tag, "PROJECT_TASK_ATTRIBUTE_DB_TABLE") {

  def attributeName: Rep[String] = column[String]("ATTRIBUTE_NAME")

  def attributeType: Rep[DBFieldType] = column[DBFieldType]("ATTRIBUTE_TYPE")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  def properties: Rep[Option[JsonObject]] = column[Option[JsonObject]]("PROPERTIES", O.Default(None))

  def order: Rep[Int] = column[Int]("ORDER")

  override def * : ProvenShape[ProjectTaskAttributeDBTableRow] = (
    id,
    attributeName,
    attributeType,
    projectId,
    properties,
    order,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectTaskAttributeDBTableRow.apply _).tupled, ProjectTaskAttributeDBTableRow.unapply)
}
