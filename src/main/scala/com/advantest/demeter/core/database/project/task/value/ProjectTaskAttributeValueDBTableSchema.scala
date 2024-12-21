package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.{DBFieldValueType, DBTableSchemaAbstract}
import slick.jdbc.MySQLProfile.api._

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
abstract class ProjectTaskAttributeValueDBTableSchema[T](tag: Tag, tableName: String) extends DBTableSchemaAbstract[T](tag, tableName) {

  def taskId: Rep[Long] = column[Long]("TASK_ID")

  def taskAttributeId: Rep[Long] = column[Long]("TASK_ATTRIBUTE_ID")

  def taskAttributeValue: Rep[_ <: DBFieldValueType]

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")
}
