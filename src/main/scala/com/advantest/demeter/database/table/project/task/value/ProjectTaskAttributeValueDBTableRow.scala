package com.advantest.demeter.database.table.project.task.value

import com.advantest.demeter.database.{DBFieldValue, DBTableRow}
import com.advantest.demeter.http.payload.ProjectTaskAttributeValuePayload

import java.time.LocalDateTime

final case class ProjectTaskAttributeValueDBTableRow(
                                                      id: Long,
                                                      taskId: Long,
                                                      taskAttributeId: Long,
                                                      taskAttributeValue: DBFieldValue,
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
