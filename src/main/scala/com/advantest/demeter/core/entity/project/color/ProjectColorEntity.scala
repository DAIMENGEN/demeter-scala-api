package com.advantest.demeter.core.entity.project.color

import com.advantest.demeter.core.database.project.color.ProjectColorTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final case class ProjectColorEntity(
                                     id: Long,
                                     value: String,
                                     description: Option[String],
                                     fieldName: String,
                                     fieldValue: String,
                                     order: Option[Int]
                                   ) {
  override def toString: String = s"ProjectColorEntity($id, $value, $description, $fieldName, $fieldValue, $order)"
}

object ProjectColorEntity extends Serializable[ProjectColorEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectColorEntity
  override protected type TableRowData = ProjectColorTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectColorEntity] = jsonFormat6(ProjectColorEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = {
    options.flatMap(_.get("projectId").map(_.asInstanceOf[Long])) match {
      case Some(projectId) => ProjectColorTableRow(
        id = entityData.id,
        value = entityData.value,
        description = entityData.description,
        fieldName = entityData.fieldName,
        fieldValue = entityData.fieldValue,
        order = entityData.order,
        projectId = projectId,
        creatorId = employeeId,
        updaterId = employeeId
      )
      case None => throw new IllegalArgumentException("projectId is required when creating a ProjectColorTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    value = entityData.value,
    description = entityData.description,
    fieldName = entityData.fieldName,
    fieldValue = entityData.fieldValue,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}