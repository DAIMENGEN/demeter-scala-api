package com.advantest.demeter.database.table.project.task.attribute

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/1
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskAttributeDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectTaskAttributeDBTableRow](tag, "PROJECT_TASK_ATTRIBUTE_DB_TABLE") {

  def taskAttributeName: Rep[DBVarcharValue] = column[DBVarcharValue]("TASK_ATTRIBUTE_NAME", O.SqlType("VARCHAR"), O.Length(255))

  def taskAttributeType: Rep[DBFieldType] = column[DBFieldType]("TASK_ATTRIBUTE_TYPE", O.SqlType("VARCHAR"), O.Length(20))

  def projectId: Rep[DBLongValue] = column[DBLongValue]("PROJECT_ID")

  def properties: Rep[Option[DBJsonValue]] = column[Option[DBJsonValue]]("PROPERTIES", O.SqlType("JSON"), O.Default(None))

  def order: Rep[DBIntValue] = column[DBIntValue]("ORDER")

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
