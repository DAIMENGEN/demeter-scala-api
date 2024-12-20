package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBIntValue
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
class ProjectTaskFieldIntTypeValueDBTableSchema(tag: Tag) extends ProjectTaskFieldValueDBTableSchema[ProjectTaskFieldValueDBTableRow](tag, "PROJECT_TASK_FIELD_INT_TYPE_VALUE_DB_TABLE") {

  def fieldValue: Rep[DBIntValue] = column[DBIntValue]("FIELD_VALUE")

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
      case fieldValue: DBIntValue => Some((row.id, row.fieldId, fieldValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}
