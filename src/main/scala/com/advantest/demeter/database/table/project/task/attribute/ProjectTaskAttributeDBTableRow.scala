package com.advantest.demeter.database.table.project.task.attribute

import com.advantest.demeter.database.{DBFieldType, DBTableRow}
import com.advantest.demeter.http.payload.ProjectTaskAttributePayload
import com.advantest.demeter.json.JsonObject

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeDBTableRow(
                                                 id: Long,
                                                 taskAttributeName: String,
                                                 taskAttributeType: DBFieldType,
                                                 projectId: Long,
                                                 properties: Option[JsonObject] = None, // 存储 column 相关的内容同，比如： fieldProps, formItemProps
                                                 order: Int,
                                                 creatorId: Long,
                                                 updaterId: Long,
                                                 createDateTime: LocalDateTime = LocalDateTime.now(),
                                                 updateDateTime: LocalDateTime = LocalDateTime.now()
                                           ) extends DBTableRow {
  override def toString: String = s"ProjectTaskAttributeDBTableRow(id=$id, taskAttributeName=$taskAttributeName, taskAttributeType=$taskAttributeType, projectId=$projectId, properties=${properties.map(_.toString)}, order=$order, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toPayload: ProjectTaskAttributePayload = {
    ProjectTaskAttributePayload(
      id = id,
      taskAttributeName = taskAttributeName,
      taskAttributeType = taskAttributeType,
      properties = properties,
      order = order
    )
  }
}