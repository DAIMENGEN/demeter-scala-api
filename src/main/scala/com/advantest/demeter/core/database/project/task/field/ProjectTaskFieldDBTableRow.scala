package com.advantest.demeter.core.database.project.task.field

import com.advantest.demeter.core.entity.project.task.field.ProjectTaskFieldEntity
import com.advantest.demeter.utils.database.{DBFieldType, DBTableRow}
import com.advantest.demeter.utils.json.JsonObject

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskFieldDBTableRow(
                                           id: Long,
                                           fieldName: String,
                                           fieldType: DBFieldType,
                                           isSystemField: Boolean,
                                           projectId: Option[Long] = None,
                                           creatorId: Long,
                                           updaterId: Long,
                                           createDateTime: LocalDateTime = LocalDateTime.now(),
                                           updateDateTime: LocalDateTime = LocalDateTime.now()
                                         ) extends DBTableRow {
  override def toString: String = s"ProjectTaskFieldTableRow(id=$id, fieldName=$fieldName, fieldType=$fieldType, isSystemField=$isSystemField, projectId=$projectId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: ProjectTaskFieldEntity = ProjectTaskFieldEntity(
    id,
    fieldName,
    fieldType,
    isSystemField
  )
}