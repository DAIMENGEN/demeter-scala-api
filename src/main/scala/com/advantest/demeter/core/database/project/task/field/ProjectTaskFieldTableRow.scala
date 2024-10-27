package com.advantest.demeter.core.database.project.task.field

import com.advantest.demeter.core.entity.project.task.ProjectTaskFieldEntity
import com.advantest.demeter.utils.database.{DBFieldType, DBTableRow}

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskFieldTableRow(
                                           id: Long,
                                           fieldName: String,
                                           fieldValue: String,
                                           fieldType: DBFieldType,
                                           description: Option[String],
                                           order: Option[Int],
                                           projectTaskId: Long,
                                           projectId: Long,
                                           creatorId: Long,
                                           updaterId: Long,
                                           createDateTime: LocalDateTime = LocalDateTime.now(),
                                           updateDateTime: LocalDateTime = LocalDateTime.now()
                                         ) extends DBTableRow {
  override def toString: String = s"ProjectTaskFieldTableRow(id=$id, fieldName=$fieldName, fieldValue=$fieldValue, fieldType=$fieldType, description=$description, order=$order, projectTaskId=$projectTaskId, projectId=$projectId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: ProjectTaskFieldEntity = ProjectTaskFieldEntity(id, fieldName, fieldValue, fieldType, description, order)
}
