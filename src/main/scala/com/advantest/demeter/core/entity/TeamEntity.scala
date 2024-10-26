package com.advantest.demeter.core.entity

import com.advantest.demeter.core.database.team.TeamTableRow
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
  override protected type TableRowData = TeamTableRow

  override implicit val serializeFormat: RootJsonFormat[TeamEntity] = jsonFormat3(TeamEntity.apply)

  override def create(employeeId: Long, entityData: TeamEntity): TeamTableRow = TeamTableRow(
    id = entityData.id,
    name = entityData.name,
    description = entityData.description,
    creatorId = employeeId,
    updaterId = employeeId
  )

  override def update(employeeId: Long, entityData: TeamEntity, oldRowData: TeamTableRow): TeamTableRow = oldRowData.copy(
    name = entityData.name,
    description = entityData.description,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
