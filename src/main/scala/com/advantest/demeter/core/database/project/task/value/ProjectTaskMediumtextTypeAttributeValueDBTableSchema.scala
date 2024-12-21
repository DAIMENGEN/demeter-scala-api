package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBMediumtextValue
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskMediumtextTypeAttributeValueDBTableSchema(tag: Tag) extends ProjectTaskAttributeValueDBTableSchema[ProjectTaskAttributeValueDBTableRow](tag, "PROJECT_TASK_MEDIUMTEXT_TYPE_ATTRIBUTE_VALUE_DB_TABLE") {

  def attributeValue: Rep[DBMediumtextValue] = column[DBMediumtextValue]("ATTRIBUTE_VALUE", O.SqlType("MEDIUMTEXT"))

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
      case attributeValue: DBMediumtextValue => Some((row.id, row.attributeId, attributeValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}
