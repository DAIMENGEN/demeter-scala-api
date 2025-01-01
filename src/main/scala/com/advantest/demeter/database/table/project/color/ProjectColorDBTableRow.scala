package com.advantest.demeter.database.table.project.color

import com.advantest.demeter.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final case class ProjectColorDBTableRow(
                                         id: Long,
                                         value: String,
                                         description: Option[String],
                                         fieldName: String,
                                         fieldValue: String,
                                         order: Option[Int],
                                         projectId: Long,
                                         creatorId: Long,
                                         updaterId: Long,
                                         createDateTime: LocalDateTime = LocalDateTime.now(),
                                         updateDateTime: LocalDateTime = LocalDateTime.now()
                                       ) extends DBTableRow {
  override def toString: String = s"ProjectColorTableRow(id=$id, value=$value, description=$description, fieldName=$fieldName, fieldValue=$fieldValue, order=$order, projectId=$projectId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}
