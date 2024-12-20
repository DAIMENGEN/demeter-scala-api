package com.advantest.demeter.core.entity.project.task.attribute

import com.advantest.demeter.core.database.project.task.attribute.ProjectTaskAttributeDBTableRow
import com.advantest.demeter.utils.database.{DBFieldType, DBTableRowFactory}
import com.advantest.demeter.utils.json.JsonObject
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeEntity(
                                             id: Long,
                                             taskAttributeName: String,
                                             taskAttributeType: DBFieldType,
                                             properties: Option[JsonObject],
                                             order: Int,
                                       ) {
  override def toString: String = s"ProjectTaskAttributeEntity(id=$id, taskAttributeName=$taskAttributeName, taskAttributeType=$taskAttributeType, properties=${properties.map(_.toString)}, order=$order)"
}

object ProjectTaskAttributeEntity extends Serializable[ProjectTaskAttributeEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskAttributeEntity
  override protected type TableRowData = ProjectTaskAttributeDBTableRow

  override implicit val serializeFormat: RootJsonFormat[EntityData] = jsonFormat5(ProjectTaskAttributeEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) =>
        ProjectTaskAttributeDBTableRow(
          id = entityData.id,
          taskAttributeName = entityData.taskAttributeName,
          taskAttributeType = entityData.taskAttributeType,
          properties = entityData.properties,
          order = entityData.order,
          projectId = projectId,
          creatorId = employeeId,
          updaterId = employeeId,
        )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskAttributeTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    taskAttributeName = entityData.taskAttributeName,
    taskAttributeType = entityData.taskAttributeType,
    properties = entityData.properties,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}