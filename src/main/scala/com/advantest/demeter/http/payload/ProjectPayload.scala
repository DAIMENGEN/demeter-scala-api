package com.advantest.demeter.http.payload

import com.advantest.demeter.constant.project.ProjectStatus
import com.advantest.demeter.database.DBTableRowFactory
import com.advantest.demeter.database.table.project.ProjectDBTableRow
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectPayload(
                                id: Long,
                                projectName: String,
                                projectStatus: ProjectStatus,
                                description: Option[String],
                                startDateTime: LocalDate,
                                endDateTime: Option[LocalDate] = None,
                                version: Option[Int] = None,
                                order: Option[Int] = None,
                              ) extends HttpPayload {
  override def toString: String = s"ProjectPayload(id=$id, projectName=$projectName, projectStatus=$projectStatus, description=$description, startDateTime=$startDateTime, endDateTime=$endDateTime, version=$version, order=$order)"
}

object ProjectPayload extends Serializable[ProjectPayload] with DBTableRowFactory {
  override protected type PayloadData = ProjectPayload
  override protected type DBTableRowData = ProjectDBTableRow

  override implicit val serializeFormat: RootJsonFormat[PayloadData] = jsonFormat8(ProjectPayload.apply)

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData = ProjectDBTableRow(
    id = payloadData.id,
    projectName = payloadData.projectName,
    projectStatus = payloadData.projectStatus,
    description = payloadData.description,
    startDateTime = payloadData.startDateTime,
    endDateTime = payloadData.endDateTime,
    version = payloadData.version,
    order = payloadData.order,
    creatorId = employeeId,
    updaterId = employeeId
  )

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    projectName = payloadData.projectName,
    projectStatus = payloadData.projectStatus,
    description = payloadData.description,
    startDateTime = payloadData.startDateTime,
    endDateTime = payloadData.endDateTime,
    version = payloadData.version,
    order = payloadData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
