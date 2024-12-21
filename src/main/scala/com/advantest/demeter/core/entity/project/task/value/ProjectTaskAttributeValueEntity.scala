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
final case class ProjectTaskAttributeValueEntity(
                                              id: Long,
                                              fieldId: Long,
                                              fieldValue: DBFieldValue,
                                            ) {
  override def toString: String = s"ProjectTaskAttributeValueEntity(id=$id, fieldId=$fieldId, fieldValue=$fieldValue)"
}

object ProjectTaskAttributeValueEntity extends Serializable[ProjectTaskAttributeValueEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskAttributeValueEntity
  override protected type TableRowData = ProjectTaskAttributeValueDBTableRow

  override implicit val serializeFormat: RootJsonFormat[EntityData] = jsonFormat3(ProjectTaskAttributeValueEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData): TableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) => ProjectTaskAttributeValueDBTableRow(
        id = entityData.id,
        attributeId = entityData.fieldId,
        attributeValue = entityData.fieldValue,
        projectId = projectId,
        creatorId = employeeId,
        updaterId = employeeId,
      )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskAttributeValueDBTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData): TableRowData = oldRowData.copy(
    attributeValue = entityData.fieldValue,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}