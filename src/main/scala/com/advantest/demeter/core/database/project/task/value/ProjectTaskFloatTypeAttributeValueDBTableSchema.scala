package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database.DBFloatTypeValue
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
final class ProjectTaskFloatTypeAttributeValueDBTableSchema(tag: Tag) extends ProjectTaskAttributeValueDBTableSchema[ProjectTaskAttributeValueDBTableRow](tag, "PROJECT_TASK_FLOAT_TYPE_ATTRIBUTE_VALUE_DB_TABLE") {

  override def taskAttributeValue: Rep[DBFloatTypeValue] = column[DBFloatTypeValue]("TASK_ATTRIBUTE_VALUE", O.SqlType("FLOAT"))

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
      case taskAttributeValue: DBFloatTypeValue => Some((row.id, row.taskId, row.taskAttributeId, taskAttributeValue, row.projectId, row.creatorId, row.updaterId, row.createDateTime, row.updateDateTime))
      case _ => None
    }
  )
}
