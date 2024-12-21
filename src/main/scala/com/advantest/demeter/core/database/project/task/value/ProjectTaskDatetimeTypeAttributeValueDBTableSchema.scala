package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBDateTimeValue
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskDatetimeTypeAttributeValueDBTableSchema(tag: Tag) extends ProjectTaskAttributeValueDBTableSchema[ProjectTaskAttributeValueDBTableRow](tag, "PROJECT_TASK_DATETIME_TYPE_ATTRIBUTE_VALUE_DB_TABLE") {

  def attributeValue: Rep[DBDateTimeValue] = column[DBDateTimeValue]("ATTRIBUTE_VALUE", O.SqlType("DATETIME"))

  override def * : ProvenShape[ProjectTaskAttributeValueDBTableRow] = (
    id,
    attributeId,
    attributeValue,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ( {
    case (id, attributeId, attributeValue, projectId, creatorId, updaterId, createDateTime, updateDateTime) => ProjectTaskAttributeValueDBTableRow(id, attributeId, attributeValue, projectId, creatorId, updaterId, createDateTime, updateDateTime)
  },
    (row: ProjectTaskAttributeValueDBTableRow) => row.attributeValue match {
      case attributeValue: DBDateTimeValue => Some((row.id, row.attributeId, attributeValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}
