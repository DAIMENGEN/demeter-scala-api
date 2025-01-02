package com.advantest.demeter.database.table.project.task.value

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBFieldValue, DBLongValue, DBTableSchemaAbstract}

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
abstract class ProjectTaskAttributeValueDBTableSchema[T](tag: Tag, tableName: String) extends DBTableSchemaAbstract[T](tag, tableName) {

  def taskId: Rep[DBLongValue] = column[DBLongValue]("TASK_ID")

  def taskAttributeId: Rep[DBLongValue] = column[DBLongValue]("TASK_ATTRIBUTE_ID")

  def taskAttributeValue: Rep[_ <: DBFieldValue]

  def projectId: Rep[DBLongValue] = column[DBLongValue]("PROJECT_ID")
}
