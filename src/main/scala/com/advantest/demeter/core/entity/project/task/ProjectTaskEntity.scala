package com.advantest.demeter.core.entity.project.task

import com.advantest.demeter.core.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.core.database.project.task.ProjectTaskDBTableRow
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
                                    taskName: String,
                                    description: Option[String],
                                    taskType: ProjectTaskType,
                                    taskStatus: ProjectTaskStatus,
                                    taskRule: Option[String],
                                    startDateTime: LocalDateTime,
                                    endDateTime: Option[LocalDateTime],
                                    parentId: Option[Long],
                                    order: Option[Int]
                                  ) {
  override def toString: String = s"ProjectTaskEntity(id=$id, taskName=$taskName, description=$description, taskType=$taskType, taskStatus=$taskStatus, taskRule=$taskRule, startDateTime=$startDateTime, endDateTime=$endDateTime, parentId=$parentId, order=$order)"
}

object ProjectTaskEntity extends Serializable[ProjectTaskEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskEntity
  override protected type TableRowData = ProjectTaskDBTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskEntity] = jsonFormat10(ProjectTaskEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = {
    options.flatMap(_.get("projectId").map(_.asInstanceOf[Long])) match {
      case Some(projectId) =>
        ProjectTaskDBTableRow(
          id = entityData.id,
          taskName = entityData.taskName,
          description = entityData.description,
          taskType = entityData.taskType,
          taskStatus = entityData.taskStatus,
          taskRule = entityData.taskRule,
          startDateTime = entityData.startDateTime,
          endDateTime = entityData.endDateTime,
          parentId = entityData.parentId,
          projectId = projectId,
          order = entityData.order,
          creatorId = employeeId,
          updaterId = employeeId
        )
      case None => throw new IllegalArgumentException("projectId is required when creating a ProjectTaskTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    taskName = entityData.taskName,
    description = entityData.description,
    taskType = entityData.taskType,
    taskStatus = entityData.taskStatus,
    taskRule = entityData.taskRule,
    startDateTime = entityData.startDateTime,
    endDateTime = entityData.endDateTime,
    parentId = entityData.parentId,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
