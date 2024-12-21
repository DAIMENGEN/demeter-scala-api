package com.advantest.demeter.core.entity.project.task.value

import com.advantest.demeter.core.database.project.task.value.ProjectTaskAttributeValueDBTableRow
import com.advantest.demeter.utils.database.{DBFieldValueType, DBTableRowFactory}
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/12/19
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeValueEntity(
                                                  id: Long,
                                                  taskId: Long,
                                                  taskAttributeId: Long,
                                                  taskAttributeValue: DBFieldValueType,
                                                ) {
  override def toString: String = s"ProjectTaskAttributeValueEntity(id=$id, taskId=$taskId, taskAttributeId=$taskAttributeId, taskAttributeValue=$taskAttributeValue)"
}

object ProjectTaskAttributeValueEntity extends Serializable[ProjectTaskAttributeValueEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskAttributeValueEntity
  override protected type TableRowData = ProjectTaskAttributeValueDBTableRow

  override implicit val serializeFormat: RootJsonFormat[EntityData] = jsonFormat4(ProjectTaskAttributeValueEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData): TableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) => ProjectTaskAttributeValueDBTableRow(
        id = entityData.id,
        taskId = entityData.taskId,
        taskAttributeId = entityData.taskAttributeId,
        taskAttributeValue = entityData.taskAttributeValue,
        projectId = projectId,
        creatorId = employeeId,
        updaterId = employeeId,
      )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskAttributeValueDBTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData): TableRowData = oldRowData.copy(
    taskAttributeValue = entityData.taskAttributeValue,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}