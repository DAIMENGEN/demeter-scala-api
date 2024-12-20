package com.advantest.demeter.core.entity.project.task.value

import com.advantest.demeter.core.database.project.task.value.ProjectTaskAttributeValueDBTableRow
import com.advantest.demeter.utils.database.{DBFieldValue, DBTableRowFactory}
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/12/19
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskFieldValueEntity(
                                              id: Long,
                                              fieldId: Long,
                                              fieldValue: DBFieldValue,
                                            ) {
  override def toString: String = s"ProjectTaskFieldValueEntity(id=$id, fieldId=$fieldId, fieldValue=$fieldValue)"
}

object ProjectTaskFieldValueEntity extends Serializable[ProjectTaskFieldValueEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskFieldValueEntity
  override protected type TableRowData = ProjectTaskAttributeValueDBTableRow

  override implicit val serializeFormat: RootJsonFormat[EntityData] = jsonFormat3(ProjectTaskFieldValueEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData): TableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) => ProjectTaskAttributeValueDBTableRow(
        id = entityData.id,
        fieldId = entityData.fieldId,
        fieldValue = entityData.fieldValue,
        projectId = projectId,
        creatorId = employeeId,
        updaterId = employeeId,
      )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskFieldValueDBTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData): TableRowData = oldRowData.copy(
    fieldValue = entityData.fieldValue,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}