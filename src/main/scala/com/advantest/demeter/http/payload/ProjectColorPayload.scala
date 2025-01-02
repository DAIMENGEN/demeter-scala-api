package com.advantest.demeter.http.payload

import com.advantest.demeter.database.table.project.color.ProjectColorDBTableRow
import com.advantest.demeter.database._
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

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
        id = DBLongValue(payloadData.id),
        value = DBVarcharValue(payloadData.value),
        description = payloadData.description.map(d => DBTextValue(d)),
        fieldName = DBVarcharValue(payloadData.fieldName),
        fieldValue = DBVarcharValue(payloadData.fieldValue),
        order = payloadData.order.map(o => DBIntValue(o)),
        projectId = DBLongValue(projectId),
        creatorId = DBLongValue(employeeId),
        updaterId = DBLongValue(employeeId),
      )
      case None => throw new IllegalArgumentException("projectId is required when creating a ProjectColorTableRow")
    }
  }

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    value = DBVarcharValue(payloadData.value),
    description = payloadData.description.map(d => DBTextValue(d)),
    fieldName = DBVarcharValue(payloadData.fieldName),
    fieldValue = DBVarcharValue(payloadData.fieldValue),
    order = payloadData.order.map(o => DBIntValue(o)),
    updaterId = DBLongValue(employeeId),
    updateDateTime = DBDateTimeValue.now()
  )
}