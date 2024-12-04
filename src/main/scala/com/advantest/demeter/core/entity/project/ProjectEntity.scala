package com.advantest.demeter.core.entity.project

import com.advantest.demeter.core.constant.project.ProjectStatus
import com.advantest.demeter.core.database.project.ProjectDBTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectEntity(
                                id: Long,
                                projectName: String,
                                projectStatus: ProjectStatus,
                                description: Option[String],
                                startDateTime: LocalDate,
                                endDateTime: Option[LocalDate] = None,
                                version: Option[Int] = None,
                                order: Option[Int] = None,
                              ) {
  override def toString: String = s"ProjectEntity(id=$id, projectName=$projectName, projectStatus=$projectStatus, description=$description, startDateTime=$startDateTime, endDateTime=$endDateTime, version=$version, order=$order)"
}

object ProjectEntity extends Serializable[ProjectEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectEntity
  override protected type TableRowData = ProjectDBTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectEntity] = jsonFormat8(ProjectEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = ProjectDBTableRow(
    id = entityData.id,
    projectName = entityData.projectName,
    projectStatus = entityData.projectStatus,
    description = entityData.description,
    startDateTime = entityData.startDateTime,
    endDateTime = entityData.endDateTime,
    version = entityData.version,
    order = entityData.order,
    creatorId = employeeId,
    updaterId = employeeId
  )

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    projectName = entityData.projectName,
    projectStatus = entityData.projectStatus,
    description = entityData.description,
    startDateTime = entityData.startDateTime,
    endDateTime = entityData.endDateTime,
    version = entityData.version,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
