package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.{DBDateTimeValue, DBTableSchemaAbstract}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
class ProjectTaskFieldDatetimeTypeValueDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectTaskFieldValueDBTableRow](tag, "PROJECT_TASK_FIELD_DATETIME_TYPE_VALUE_DB_TABLE") {

  def fieldId: Rep[Long] = column[Long]("FIELD_ID")

  def fieldValue: Rep[LocalDateTime] = column[LocalDateTime]("FIELD_VALUE")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  override def * : ProvenShape[ProjectTaskFieldValueDBTableRow] = (
    id,
    fieldId,
    fieldValue,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ( {
    case (id, fieldId, fieldValue, projectId, creatorId, updaterId, createDateTime, updateDateTime) => ProjectTaskFieldValueDBTableRow(id, fieldId, DBDateTimeValue(fieldValue), projectId, creatorId, updaterId, createDateTime, updateDateTime)
  },
    (row: ProjectTaskFieldValueDBTableRow) => row.fieldValue match {
      case DBDateTimeValue(fieldValue) => Some((row.id, row.fieldId, fieldValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}
