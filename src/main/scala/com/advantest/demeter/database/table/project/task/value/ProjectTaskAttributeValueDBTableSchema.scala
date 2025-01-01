package com.advantest.demeter.database.table.project.task.value

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBFieldValue, DBTableSchemaAbstract}

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
abstract class ProjectTaskAttributeValueDBTableSchema[T](tag: Tag, tableName: String) extends DBTableSchemaAbstract[T](tag, tableName) {

  def taskId: Rep[Long] = column[Long]("TASK_ID")

  def taskAttributeId: Rep[Long] = column[Long]("TASK_ATTRIBUTE_ID")

  def taskAttributeValue: Rep[_ <: DBFieldValue]

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")
}
