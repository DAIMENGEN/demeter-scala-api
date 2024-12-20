package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBTableSchemaAbstract
import slick.jdbc.MySQLProfile.api._

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
abstract class ProjectTaskFieldValueDBTableSchema[T](tag: Tag, tableName: String) extends DBTableSchemaAbstract[T](tag, tableName) {

  def fieldId: Rep[Long] = column[Long]("FIELD_ID")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")
}
