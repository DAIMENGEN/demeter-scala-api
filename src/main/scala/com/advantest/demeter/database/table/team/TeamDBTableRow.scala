package com.advantest.demeter.database.table.team

import com.advantest.demeter.database._
import com.advantest.demeter.http.payload.TeamPayload

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class TeamDBTableRow(
                                 id: DBLongValue,
                                 name: DBVarcharValue,
                                 description: Option[DBTextValue],
                                 creatorId: DBLongValue,
                                 updaterId: DBLongValue,
                                 createDateTime: DBDateTimeValue = DBDateTimeValue.now(),
                                 updateDateTime: DBDateTimeValue = DBDateTimeValue.now()
                             ) extends DBTableRow {

  def toEntity: TeamPayload = TeamPayload(
    id = id.value,
    name = name.value,
    description = description.map(_.value)
  )
}
