package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBDateValue
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
class ProjectTaskDateTypeAttributeValueDBTableSchema(tag: Tag) extends ProjectTaskAttributeValueDBTableSchema[ProjectTaskAttributeValueDBTableRow](tag, "PROJECT_TASK_DATE_TYPE_ATTRIBUTE_VALUE_DB_TABLE") {

  def fieldValue: Rep[DBDateValue] = column[DBDateValue]("FIELD_VALUE", O.SqlType("DATE"))

  override def * : ProvenShape[ProjectTaskAttributeValueDBTableRow] = (
    id,
    fieldId,
    fieldValue,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ( {
    case (id, fieldId, fieldValue, projectId, creatorId, updaterId, createDateTime, updateDateTime) => ProjectTaskAttributeValueDBTableRow(id, fieldId, fieldValue, projectId, creatorId, updaterId, createDateTime, updateDateTime)
  },
    (row: ProjectTaskAttributeValueDBTableRow) => row.fieldValue match {
      case fieldValue: DBDateValue => Some((row.id, row.fieldId, fieldValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}