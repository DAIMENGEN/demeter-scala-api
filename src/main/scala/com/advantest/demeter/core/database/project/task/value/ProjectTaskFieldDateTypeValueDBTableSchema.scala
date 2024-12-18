package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.{DBDateValue, DBTableSchemaAbstract}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDate

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
class ProjectTaskFieldDateTypeValueDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[ProjectTaskFieldValueDBTableRow](tag, "PROJECT_TASK_FIELD_DATE_TYPE_VALUE_DB_TABLE") {

  def fieldId: Rep[Long] = column[Long]("FIELD_ID")

  def fieldValue: Rep[LocalDate] = column[LocalDate]("FIELD_VALUE")

  override def * : ProvenShape[ProjectTaskFieldValueDBTableRow] = (
    id,
    fieldId,
    fieldValue,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ( {
    case (id, fieldId, value, creatorId, updaterId, createDateTime, updateDateTime) => ProjectTaskFieldValueDBTableRow(id, fieldId, DBDateValue(value), creatorId, updaterId, createDateTime, updateDateTime)
  },
    (row: ProjectTaskFieldValueDBTableRow) => row.fieldValue match {
      case DBDateValue(value) => Some((row.id, row.fieldId, value, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}