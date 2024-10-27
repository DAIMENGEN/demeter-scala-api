package com.advantest.demeter.core.database.project

import com.advantest.demeter.core.constant.project.ProjectStatus
import com.advantest.demeter.core.entity.ProjectEntity
import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTableRow(
                            id: Long,
                            name: String,
                            description: Option[String],
                            startDateTime: LocalDateTime,
                            endDateTime: Option[LocalDateTime],
                            status: ProjectStatus,
                            version: Option[Int],
                            order: Option[Int],
                            creatorId: Long,
                            updaterId: Long,
                            createDateTime: LocalDateTime = LocalDateTime.now(),
                            updateDateTime: LocalDateTime = LocalDateTime.now()
                          ) extends DBTableRow {
  override def toString: String = s"ProjectTableRow(id=$id, name=$name, description=$description, startDateTime=$startDateTime, endDateTime=$endDateTime, status=$status, version=$version, order=$order, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: ProjectEntity = ProjectEntity(
    id,
    name,
    status,
    description,
    startDateTime,
    endDateTime,
    version,
    order
  )
}
