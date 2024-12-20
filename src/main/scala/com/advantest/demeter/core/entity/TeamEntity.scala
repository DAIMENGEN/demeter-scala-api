package com.advantest.demeter.core.entity

import com.advantest.demeter.core.database.team.TeamDBTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class TeamEntity(
                             id: Long,
                             name: String,
                             description: Option[String]
                           ) {
  override def toString: String = s"TeamEntity(id=$id, name=$name, description=$description)"
}

object TeamEntity extends Serializable[TeamEntity] with DBTableRowFactory {
  override protected type EntityData = TeamEntity
  override protected type TableRowData = TeamDBTableRow

  override implicit val serializeFormat: RootJsonFormat[EntityData] = jsonFormat3(TeamEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = TeamDBTableRow(
    id = entityData.id,
    name = entityData.name,
    description = entityData.description,
    creatorId = employeeId,
    updaterId = employeeId
  )

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    name = entityData.name,
    description = entityData.description,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
