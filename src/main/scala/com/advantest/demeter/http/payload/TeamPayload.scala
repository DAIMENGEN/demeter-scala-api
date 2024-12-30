package com.advantest.demeter.http.payload

import com.advantest.demeter.database.DBTableRowFactory
import com.advantest.demeter.database.table.team.TeamDBTableRow
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class TeamPayload(
                             id: Long,
                             name: String,
                             description: Option[String]
                           ) extends HttpPayload {
  override def toString: String = s"TeamPayload(id=$id, name=$name, description=$description)"
}

object TeamPayload extends Serializable[TeamPayload] with DBTableRowFactory {
  override protected type PayloadData = TeamPayload
  override protected type DBTableRowData = TeamDBTableRow

  override implicit val serializeFormat: RootJsonFormat[PayloadData] = jsonFormat3(TeamPayload.apply)

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData = TeamDBTableRow(
    id = payloadData.id,
    name = payloadData.name,
    description = payloadData.description,
    creatorId = employeeId,
    updaterId = employeeId
  )

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    name = payloadData.name,
    description = payloadData.description,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
