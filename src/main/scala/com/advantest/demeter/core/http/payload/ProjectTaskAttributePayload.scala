package com.advantest.demeter.core.http.payload

import com.advantest.demeter.core.database.project.task.attribute.ProjectTaskAttributeDBTableRow
import com.advantest.demeter.utils.database.{DBFieldType, DBTableRowFactory}
import com.advantest.demeter.utils.http.HttpPayload
import com.advantest.demeter.utils.json.JsonObject
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributePayload(
                                             id: Long,
                                             taskAttributeName: String,
                                             taskAttributeType: DBFieldType,
                                             properties: Option[JsonObject],
                                             order: Int,
                                       ) extends HttpPayload {
  override def toString: String = s"ProjectTaskAttributePayload(id=$id, taskAttributeName=$taskAttributeName, taskAttributeType=$taskAttributeType, properties=${properties.map(_.toString)}, order=$order)"
}

object ProjectTaskAttributePayload extends Serializable[ProjectTaskAttributePayload] with DBTableRowFactory {
  override protected type PayloadData = ProjectTaskAttributePayload
  override protected type DBTableRowData = ProjectTaskAttributeDBTableRow

  override implicit val serializeFormat: RootJsonFormat[PayloadData] = jsonFormat5(ProjectTaskAttributePayload.apply)

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData = {
    val maybeProjectId = options.flatMap(_.get("projectId").map(_.asInstanceOf[Long]))
    maybeProjectId match {
      case Some(projectId) =>
        ProjectTaskAttributeDBTableRow(
          id = payloadData.id,
          taskAttributeName = payloadData.taskAttributeName,
          taskAttributeType = payloadData.taskAttributeType,
          properties = payloadData.properties,
          order = payloadData.order,
          projectId = projectId,
          creatorId = employeeId,
          updaterId = employeeId,
        )
      case None => throw new IllegalArgumentException("ProjectId are required when creating a ProjectTaskAttributeTableRow")
    }
  }

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    taskAttributeName = payloadData.taskAttributeName,
    taskAttributeType = payloadData.taskAttributeType,
    properties = payloadData.properties,
    order = payloadData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}