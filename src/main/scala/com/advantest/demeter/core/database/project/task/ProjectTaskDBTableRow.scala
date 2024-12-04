package com.advantest.demeter.core.database.project.task

import com.advantest.demeter.core.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.core.entity.project.task.ProjectTaskEntity
import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskDBTableRow(
                                      id: Long,
                                      taskName: String,
                                      description: Option[String],
                                      taskType: ProjectTaskType,
                                      taskStatus: ProjectTaskStatus,
                                      taskRule: Option[String],
                                      startDateTime: LocalDateTime,
                                      endDateTime: Option[LocalDateTime],
                                      parentId: Option[Long],
                                      projectId: Long,
                                      order: Option[Int],
                                      creatorId: Long,
                                      updaterId: Long,
                                      createDateTime: LocalDateTime = LocalDateTime.now(),
                                      updateDateTime: LocalDateTime = LocalDateTime.now()
                                    ) extends DBTableRow {
  override def toString: String = s"ProjectTaskTableRow(id=$id, taskName=$taskName, description=$description, taskType=$taskType, taskStatus=$taskStatus, taskRule=$taskRule, startDateTime=$startDateTime, endDateTime=$endDateTime, parentId=$parentId, projectId=$projectId, order=$order, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: ProjectTaskEntity = ProjectTaskEntity(
    id,
    taskName,
    description,
    taskType,
    taskStatus,
    taskRule,
    startDateTime,
    endDateTime,
    parentId,
    order
  )
}
