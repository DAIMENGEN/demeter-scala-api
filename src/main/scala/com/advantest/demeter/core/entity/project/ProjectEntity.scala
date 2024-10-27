package com.advantest.demeter.core.entity.project

import com.advantest.demeter.core.constant.project.ProjectStatus
import com.advantest.demeter.core.database.project.ProjectTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectEntity(
                                id: Long,
                                name: String,
                                status: ProjectStatus,
                                description: Option[String],
                                startDateTime: LocalDateTime,
                                endDateTime: Option[LocalDateTime] = None,
                                version: Option[Int] = None,
                                order: Option[Int] = None,
                              ) {
  override def toString: String = s"ProjectEntity(id=$id, name=$name, status=$status, description=$description, startDateTime=$startDateTime, endDateTime=$endDateTime, version=$version, order=$order)"
}

object ProjectEntity extends Serializable[ProjectEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectEntity
  override protected type TableRowData = ProjectTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectEntity] = jsonFormat8(ProjectEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = ProjectTableRow(
    id = entityData.id,
    name = entityData.name,
    status = entityData.status,
    description = entityData.description,
    startDateTime = entityData.startDateTime,
    endDateTime = entityData.endDateTime,
    version = entityData.version,
    order = entityData.order,
    creatorId = employeeId,
    updaterId = employeeId
  )

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    name = entityData.name,
    status = entityData.status,
    description = entityData.description,
    startDateTime = entityData.startDateTime,
    endDateTime = entityData.endDateTime,
    version = entityData.version,
    order = entityData.order,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
