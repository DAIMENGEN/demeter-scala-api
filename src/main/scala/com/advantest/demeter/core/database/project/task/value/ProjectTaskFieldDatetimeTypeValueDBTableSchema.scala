package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBDateTimeValue
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
class ProjectTaskFieldDatetimeTypeValueDBTableSchema(tag: Tag) extends ProjectTaskFieldValueDBTableSchema[ProjectTaskFieldValueDBTableRow](tag, "PROJECT_TASK_FIELD_DATETIME_TYPE_VALUE_DB_TABLE") {

  def fieldValue: Rep[DBDateTimeValue] = column[DBDateTimeValue]("FIELD_VALUE", O.SqlType("DATETIME"))

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
    case (id, fieldId, fieldValue, projectId, creatorId, updaterId, createDateTime, updateDateTime) => ProjectTaskFieldValueDBTableRow(id, fieldId, fieldValue, projectId, creatorId, updaterId, createDateTime, updateDateTime)
  },
    (row: ProjectTaskFieldValueDBTableRow) => row.fieldValue match {
      case fieldValue: DBDateTimeValue => Some((row.id, row.fieldId, fieldValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}
