package com.advantest.demeter.core.entity.project.task

import com.advantest.demeter.core.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.core.database.project.task.ProjectTaskTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskEntity(
                                    id: Long,
                                    name: String,
                                    description: Option[String],
                                    taskType: ProjectTaskType,
                                    taskStatus: ProjectTaskStatus,
                                    taskRule: Option[String],
                                    startDateTime: LocalDateTime,
                                    endDateTime: Option[LocalDateTime],
                                    parentId: Option[Long],
                                    projectId: Long,
                                    order: Option[Int]
                                  ) {
  override def toString: String = s"ProjectTaskEntity($id, $name, $description, $taskType, $taskStatus, $taskRule, $startDateTime, $endDateTime, $parentId, $projectId, $order)"
}

object ProjectTaskEntity extends Serializable[ProjectTaskEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskEntity
  override protected type TableRowData = ProjectTaskTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskEntity] = jsonFormat11(ProjectTaskEntity.apply)

  override def create(employeeId: Long, entityData: ProjectTaskEntity): ProjectTaskTableRow = ProjectTaskTableRow(
    id = entityData.id,
    name = entityData.name,
    description = entityData.description,
    taskType = entityData.taskType,
    taskStatus = entityData.taskStatus,
    taskRule = entityData.taskRule,
    startDateTime = entityData.startDateTime,
    endDateTime = entityData.endDateTime,
    parentId = entityData.parentId,
    projectId = entityData.projectId,
    order = entityData.order,
    creatorId = employeeId,
    updaterId = employeeId
  )

  override def update(employeeId: Long, entityData: ProjectTaskEntity, oldRowData: ProjectTaskTableRow): ProjectTaskTableRow = oldRowData.copy(
    name = entityData.name,
    description = entityData.description,
    taskType = entityData.taskType,
    taskStatus = entityData.taskStatus,
    taskRule = entityData.taskRule,
    startDateTime = entityData.startDateTime,
    endDateTime = entityData.endDateTime,
    parentId = entityData.parentId,
    projectId = entityData.projectId,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
