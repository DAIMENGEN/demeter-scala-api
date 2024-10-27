package com.advantest.demeter.core.entity.project.task

import com.advantest.demeter.core.database.project.task.field.ProjectTaskFieldTableRow
import com.advantest.demeter.utils.database.{DBFieldType, DBTableRowFactory}
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
                                         fieldValue: String,
                                         fieldType: DBFieldType,
                                         description: Option[String],
                                         order: Option[Int]
                                       ) {
  override def toString: String = s"ProjectTaskFieldEntity(id=$id, fieldName=$fieldName, fieldValue=$fieldValue, fieldType=$fieldType, description=$description, order=$order)"
}

object ProjectTaskFieldEntity extends Serializable[ProjectTaskFieldEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskFieldEntity
  override protected type TableRowData = ProjectTaskFieldTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskFieldEntity] = jsonFormat6(ProjectTaskFieldEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    val maybeProjectTaskId = options.flatMap(_.get("projectTaskId").map(_.asInstanceOf[Long]))
    (maybeProjectId, maybeProjectTaskId) match {
      case (Some(projectId), Some(projectTaskId)) =>
        ProjectTaskFieldTableRow(
          id = entityData.id,
          fieldName = entityData.fieldName,
          fieldValue = entityData.fieldValue,
          fieldType = entityData.fieldType,
          description = entityData.description,
          order = entityData.order,
          projectId = projectId,
          projectTaskId = projectTaskId,
          creatorId = employeeId,
          updaterId = employeeId,
        )
      case _ => throw new IllegalArgumentException("Both projectId and projectTaskId are required when creating a ProjectTaskFieldTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    fieldName = entityData.fieldName,
    fieldValue = entityData.fieldValue,
    fieldType = entityData.fieldType,
    description = entityData.description,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}