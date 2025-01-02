package com.advantest.demeter.http.payload

import com.advantest.demeter.database.table.project.task.value.ProjectTaskAttributeValueDBTableRow
import com.advantest.demeter.database.{DBDateTimeValue, DBFieldValue, DBLongValue, DBTableRowFactory}
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

/**
 * Create on 2024/12/19
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeValuePayload(
                                                   id: Long,
                                                   taskId: Long,
                                                   taskAttributeId: Long,
                                                   taskAttributeValue: DBFieldValue,
                                                 ) extends HttpPayload {
  override def toString: String = s"ProjectTaskAttributeValuePayload(id=$id, taskId=$taskId, taskAttributeId=$taskAttributeId, taskAttributeValue=$taskAttributeValue)"
}

object ProjectTaskAttributeValuePayload extends Serializable[ProjectTaskAttributeValuePayload] with DBTableRowFactory {
  override protected type PayloadData = ProjectTaskAttributeValuePayload
  override protected type DBTableRowData = ProjectTaskAttributeValueDBTableRow

  override implicit val serializeFormat: RootJsonFormat[PayloadData] = jsonFormat4(ProjectTaskAttributeValuePayload.apply)

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData): DBTableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) => ProjectTaskAttributeValueDBTableRow(
        id = DBLongValue(payloadData.id),
        taskId = DBLongValue(payloadData.taskId),
        taskAttributeId = DBLongValue(payloadData.taskAttributeId),
        taskAttributeValue = payloadData.taskAttributeValue,
        projectId = DBLongValue(projectId),
        creatorId = DBLongValue(employeeId),
        updaterId = DBLongValue(employeeId),
      )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskAttributeValueDBTableRow")
    }
  }

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData): DBTableRowData = oldRowData.copy(
    taskAttributeValue = payloadData.taskAttributeValue,
    updaterId = DBLongValue(employeeId),
    updateDateTime = DBDateTimeValue.now()
  )
}