package com.advantest.demeter.core.http.payload

import com.advantest.demeter.core.database.team.TeamDBTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.http.HttpPayload
import com.advantest.demeter.utils.serialize.Serializable
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
