package com.advantest.demeter.core.entity.project.task

import com.advantest.demeter.core.database.project.task.ProjectTaskDBTableRow
import com.advantest.demeter.core.entity.project.task.attribute.ProjectTaskAttributeEntity
import com.advantest.demeter.core.entity.project.task.value.ProjectTaskAttributeValueEntity
import com.advantest.demeter.utils.database._
import com.advantest.demeter.utils.serialize.{Serializable, format}
import spray.json.DefaultJsonProtocol._
import spray.json._

import java.time.LocalDateTime

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskEntity(
                                    id: Long,
                                    taskName: String,
                                    taskAttributes: Seq[ProjectTaskAttributeEntity] = Seq.empty,
                                    taskAttributeValues: Seq[ProjectTaskAttributeValueEntity] = Seq.empty,
                                    order: Option[Int] = None
                                  ) {
  // To support cases where attribute values can be empty, the lengths of taskAttributes and taskAttributeValues are validated.
  assert(taskAttributes.size >= taskAttributeValues.size, "taskAttributes.size must bigger than taskAttributeValues.size.")

  override def toString: String = s"ProjectTaskEntity(id=$id, taskName=$taskName, order=$order)"
}

object ProjectTaskEntity extends Serializable[ProjectTaskEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectTaskEntity
  override protected type TableRowData = ProjectTaskDBTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskEntity] = new RootJsonFormat[ProjectTaskEntity] {
    override def write(obj: ProjectTaskEntity): JsValue = {
      val writer = format.writer.JsonWriterFormat()
      val attributes = obj.taskAttributes.map(attribute => attribute.id -> attribute.taskAttributeName).toMap
      obj.taskAttributeValues.collect {
        case attributeValue: ProjectTaskAttributeValueEntity if attributes.contains(attributeValue.taskAttributeId) => attributes(attributeValue.taskAttributeId) -> attributeValue
      }.foreach {
        case (attributeName, attributeValue) =>
          attributeValue.taskAttributeValue match {
            case DBIntTypeValue(value) => writer.writeInt(attributeName, value)
            case DBLongTypeValue(value) => writer.writeLong(attributeName, value)
            case DBFloatTypeValue(value) => writer.writeFloat(attributeName, value)
            case DBDoubleTypeValue(value) => writer.writeDouble(attributeName, value)
            case DBStringTypeValue(value) => writer.writeString(attributeName, value)
            case DBTextTypeValue(value) => writer.writeString(attributeName, value)
            case DBMediumtextTypeValue(value) => writer.writeString(attributeName, value)
            case DBLongtextTypeValue(value) => writer.writeString(attributeName, value)
            case DBBooleanTypeValue(value) => writer.writeBoolean(attributeName, value)
            case DBJsonTypeValue(value) => writer.writeString(attributeName, value.toString)
            case DBDateTypeValue(value) => writer.writeLocalDate(attributeName, value)
            case DBDateTimeTypeValue(value) => writer.writeLocalDateTime(attributeName, value)
          }
      }
      writer.writeString("taskName", obj.taskName).writeInt("order", obj.order).toJsObject
    }

    override def read(json: JsValue): ProjectTaskEntity = {
      json match {
        case JsObject(fields) =>
          val reader = format.reader.JsonReaderFormat(fields)
          val id = reader.readLong("id")
          val order = reader.readOptionInt("order")
          val taskName = reader.readString("taskName")
          val taskAttributes = reader.read[Seq[ProjectTaskAttributeEntity]]("taskAttributes")
          val taskAttributeValues = reader.read[Seq[ProjectTaskAttributeValueEntity]]("taskAttributeValues")
          ProjectTaskEntity(id, taskName, taskAttributes, taskAttributeValues, order)
        case _ => deserializationError("ProjectTaskEntity deserialization error.")
      }
    }
  }

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) =>
        ProjectTaskDBTableRow(
          id = entityData.id,
          taskName = entityData.taskName,
          order = entityData.order,
          projectId = projectId,
          creatorId = employeeId,
          updaterId = employeeId,
        )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskDBTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: ProjectTaskDBTableRow, options: OptionalData = None): TableRowData = oldRowData.copy(
    taskName = entityData.taskName,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}