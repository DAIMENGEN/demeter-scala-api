package com.advantest.demeter.http.payload

import com.advantest.demeter.database.table.project.task.ProjectTaskDBTableRow
import com.advantest.demeter.database._
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import com.advantest.demeter.json.serialize.format.reader.JsonReaderFormat
import com.advantest.demeter.json.serialize.format.writer.JsonWriterFormat
import spray.json.DefaultJsonProtocol._
import spray.json._

import java.time.LocalDateTime

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskPayload(
                                     id: Long,
                                     taskName: String,
                                     taskAttributes: Seq[ProjectTaskAttributePayload] = Seq.empty,
                                     taskAttributeValues: Seq[ProjectTaskAttributeValuePayload] = Seq.empty,
                                     order: Option[Int] = None
                                   ) extends HttpPayload {
  // To support cases where attribute values can be empty, the lengths of taskAttributes and taskAttributeValues are validated.
  assert(taskAttributes.size >= taskAttributeValues.size, "taskAttributes.size must bigger than taskAttributeValues.size.")

  override def toString: String = s"ProjectTaskPayload(id=$id, taskName=$taskName, order=$order)"
}

object ProjectTaskPayload extends Serializable[ProjectTaskPayload] with DBTableRowFactory {
  override protected type PayloadData = ProjectTaskPayload
  override protected type DBTableRowData = ProjectTaskDBTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskPayload] = new RootJsonFormat[ProjectTaskPayload] {
    override def write(obj: ProjectTaskPayload): JsValue = {
      val writer = JsonWriterFormat()
      val attributes = obj.taskAttributes.map(attribute => attribute.id -> attribute.taskAttributeName).toMap
      obj.taskAttributeValues.collect {
        case attributeValue: ProjectTaskAttributeValuePayload if attributes.contains(attributeValue.taskAttributeId) => attributes(attributeValue.taskAttributeId) -> attributeValue
      }.foreach {
        case (attributeName, attributeValue) =>
          attributeValue.taskAttributeValue match {
            case DBIntValue(value) => writer.writeInt(attributeName, value)
            case DBLongValue(value) => writer.writeLong(attributeName, value)
            case DBFloatValue(value) => writer.writeFloat(attributeName, value)
            case DBDoubleValue(value) => writer.writeDouble(attributeName, value)
            case DBVarcharValue(value) => writer.writeString(attributeName, value)
            case DBTextValue(value) => writer.writeString(attributeName, value)
            case DBMediumtextValue(value) => writer.writeString(attributeName, value)
            case DBLongtextValue(value) => writer.writeString(attributeName, value)
            case DBBooleanValue(value) => writer.writeBoolean(attributeName, value)
            case DBJsonValue(value) => writer.writeString(attributeName, value.toString)
            case DBDateValue(value) => writer.writeLocalDate(attributeName, value)
            case DBDateTimeValue(value) => writer.writeLocalDateTime(attributeName, value)
          }
      }
      writer.writeString("taskName", obj.taskName).writeInt("order", obj.order).toJsObject
    }

    override def read(json: JsValue): ProjectTaskPayload = {
      json match {
        case JsObject(fields) =>
          val reader = JsonReaderFormat(fields)
          val id = reader.readLong("id")
          val order = reader.readOptionInt("order")
          val taskName = reader.readString("taskName")
          val taskAttributes = reader.read[Seq[ProjectTaskAttributePayload]]("taskAttributes")
          val taskAttributeValues = reader.read[Seq[ProjectTaskAttributeValuePayload]]("taskAttributeValues")
          ProjectTaskPayload(id, taskName, taskAttributes, taskAttributeValues, order)
        case _ => deserializationError("ProjectTaskEntity deserialization error.")
      }
    }
  }

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) =>
        ProjectTaskDBTableRow(
          id = payloadData.id,
          taskName = payloadData.taskName,
          order = payloadData.order,
          projectId = projectId,
          creatorId = employeeId,
          updaterId = employeeId,
        )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskDBTableRow")
    }
  }

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: ProjectTaskDBTableRow, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    taskName = payloadData.taskName,
    order = payloadData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}