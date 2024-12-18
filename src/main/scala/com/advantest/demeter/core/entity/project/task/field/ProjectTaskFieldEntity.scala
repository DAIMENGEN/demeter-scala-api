package com.advantest.demeter.core.entity.project.task.field

import com.advantest.demeter.core.database.project.task.field.ProjectTaskFieldDBTableRow
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
final case class ProjectTaskFieldEntity(
                                         id: Long,
                                         fieldName: String,
                                         fieldType: DBFieldType,
                                         properties: Option[JsonObject],
                                         order: Int,
                                       ) {

}

object ProjectTaskFieldEntity extends Serializable[ProjectTaskFieldEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskFieldEntity
  override protected type TableRowData = ProjectTaskFieldDBTableRow

  override implicit val serializeFormat: RootJsonFormat[EntityData] = jsonFormat5(ProjectTaskFieldEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) =>
        ProjectTaskFieldDBTableRow(
          id = entityData.id,
          fieldName = entityData.fieldName,
          fieldType = entityData.fieldType,
          properties = entityData.properties,
          order = entityData.order,
          projectId = projectId,
          creatorId = employeeId,
          updaterId = employeeId,
        )
      case _ => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskFieldTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    fieldName = entityData.fieldName,
    fieldType = entityData.fieldType,
    properties = entityData.properties,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}