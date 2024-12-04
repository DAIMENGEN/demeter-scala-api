package com.advantest.demeter.core.database.project.task.table

import com.advantest.demeter.utils.database.DBTableRow
import com.advantest.demeter.utils.json.JsonObject

import java.time.LocalDateTime

/**
 * Create on 2024/12/4
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskTableColumnDBTableRow(
                                                 id: Long,
                                                 key: String,
                                                 title: String,
                                                 dataIndex: String,
                                                 valueType: String,
                                                 description: Option[String] = None,
                                                 filedProps: Option[JsonObject] = None,
                                                 formItemProps: Option[JsonObject] = None,
                                                 projectId: Long,
                                                 creatorId: Long,
                                                 updaterId: Long,
                                                 createDateTime: LocalDateTime = LocalDateTime.now(),
                                                 updateDateTime: LocalDateTime = LocalDateTime.now()
                                               ) extends DBTableRow {
  override def toString: String = s"ProjectTaskTableColumnDBTableRow(id=$id, key=$key, title=$title, dataIndex=$dataIndex, valueType=$valueType, description=$description, filedProps=${filedProps.map(_.toString)}, formItemProps=${formItemProps.map(_.toString)}, projectId=$projectId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}
