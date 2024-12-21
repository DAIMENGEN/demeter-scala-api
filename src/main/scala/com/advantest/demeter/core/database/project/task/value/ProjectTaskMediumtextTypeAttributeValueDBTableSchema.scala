package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBMediumtextTypeValue
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskMediumtextTypeAttributeValueDBTableSchema(tag: Tag) extends ProjectTaskAttributeValueDBTableSchema[ProjectTaskAttributeValueDBTableRow](tag, "PROJECT_TASK_MEDIUMTEXT_TYPE_ATTRIBUTE_VALUE_DB_TABLE") {

  override def taskAttributeValue: Rep[DBMediumtextTypeValue] = column[DBMediumtextTypeValue]("TASK_ATTRIBUTE_VALUE", O.SqlType("MEDIUMTEXT"))

  override def * : ProvenShape[ProjectTaskAttributeValueDBTableRow] = (
    id,
    taskId,
    taskAttributeId,
    taskAttributeValue,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ( {
    case (id, taskId, taskAttributeId, taskAttributeValue, projectId, creatorId, updaterId, createDateTime, updateDateTime) => ProjectTaskAttributeValueDBTableRow(id, taskId, taskAttributeId, taskAttributeValue, projectId, creatorId, updaterId, createDateTime, updateDateTime)
  },
    (row: ProjectTaskAttributeValueDBTableRow) => row.taskAttributeValue match {
      case taskAttributeValue: DBMediumtextTypeValue => Some((row.id, row.taskId, row.taskAttributeId, taskAttributeValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}
