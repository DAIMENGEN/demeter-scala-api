package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.core.http.payload.ProjectTaskAttributeValuePayload
import com.advantest.demeter.utils.database.{DBFieldValueType, DBTableRow}

import java.time.LocalDateTime

final case class ProjectTaskAttributeValueDBTableRow(
                                                      id: Long,
                                                      taskId: Long,
                                                      taskAttributeId: Long,
                                                      taskAttributeValue: DBFieldValueType,
                                                      projectId: Long,
                                                      creatorId: Long,
                                                      updaterId: Long,
                                                      createDateTime: LocalDateTime = LocalDateTime.now(),
                                                      updateDateTime: LocalDateTime = LocalDateTime.now()
                                                    ) extends DBTableRow {
  override def toString: String = s"ProjectTaskAttributeValueDBTableRow(id=$id, taskId=$taskId, taskAttributeId=$taskAttributeId, taskAttributeValue=$taskAttributeValue, projectId=$projectId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toPayload: ProjectTaskAttributeValuePayload = ProjectTaskAttributeValuePayload(
    id,
    taskId,
    taskAttributeId,
    taskAttributeValue
  )
}
