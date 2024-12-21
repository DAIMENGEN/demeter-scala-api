package com.advantest.demeter.core.database.project.task.attribute

import com.advantest.demeter.core.entity.project.task.attribute.ProjectTaskAttributeEntity
import com.advantest.demeter.utils.database.{DBFieldType, DBTableRow}
import com.advantest.demeter.utils.json.JsonObject

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeDBTableRow(
                                             id: Long,
                                             fieldName: String,
                                             fieldType: DBFieldType,
                                             projectId: Long,
                                             properties: Option[JsonObject] = None, // 存储 column 相关的内容同，比如： fieldProps, formItemProps
                                             order: Int,
                                             creatorId: Long,
                                             updaterId: Long,
                                             createDateTime: LocalDateTime = LocalDateTime.now(),
                                             updateDateTime: LocalDateTime = LocalDateTime.now()
                                           ) extends DBTableRow {
  override def toString: String = s"ProjectTaskFieldDBTableRow(id=$id, fieldName=$fieldName, fieldType=$fieldType, projectId=$projectId, properties=${properties.map(_.toString)}, order=$order, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: ProjectTaskAttributeEntity = {
    ProjectTaskAttributeEntity(
      id = id,
      fieldName = fieldName,
      fieldType = fieldType,
      properties = properties,
      order = order
    )
  }
}