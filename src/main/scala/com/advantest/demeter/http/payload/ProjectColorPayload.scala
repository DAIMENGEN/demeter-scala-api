package com.advantest.demeter.http.payload

import com.advantest.demeter.database.DBTableRowFactory
import com.advantest.demeter.database.table.project.color.ProjectColorDBTableRow
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final case class ProjectColorPayload(
                                     id: Long,
                                     value: String,
                                     description: Option[String],
                                     fieldName: String,
                                     fieldValue: String,
                                     order: Option[Int]
                                   ) extends HttpPayload {
  override def toString: String = s"ProjectColorPayload(id=$id, value=$value, description=$description, fieldName=$fieldName, fieldValue=$fieldValue, order=$order)"
}

object ProjectColorPayload extends Serializable[ProjectColorPayload] with DBTableRowFactory {
  override protected type PayloadData = ProjectColorPayload
  override protected type DBTableRowData = ProjectColorDBTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectColorPayload] = jsonFormat6(ProjectColorPayload.apply)

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData = {
    options.flatMap(_.get("projectId").map(_.asInstanceOf[Long])) match {
      case Some(projectId) => ProjectColorDBTableRow(
        id = payloadData.id,
        value = payloadData.value,
        description = payloadData.description,
        fieldName = payloadData.fieldName,
        fieldValue = payloadData.fieldValue,
        order = payloadData.order,
        projectId = projectId,
        creatorId = employeeId,
        updaterId = employeeId
      )
      case None => throw new IllegalArgumentException("projectId is required when creating a ProjectColorTableRow")
    }
  }

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    value = payloadData.value,
    description = payloadData.description,
    fieldName = payloadData.fieldName,
    fieldValue = payloadData.fieldValue,
    order = payloadData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}