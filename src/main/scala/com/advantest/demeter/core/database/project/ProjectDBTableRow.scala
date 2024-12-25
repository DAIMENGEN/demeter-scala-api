package com.advantest.demeter.core.database.project

import com.advantest.demeter.core.constant.project.ProjectStatus
import com.advantest.demeter.core.http.payload.ProjectPayload
import com.advantest.demeter.utils.database.DBTableRow

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectDBTableRow(
                                  id: Long,
                                  projectName: String,
                                  description: Option[String],
                                  startDateTime: LocalDate,
                                  endDateTime: Option[LocalDate],
                                  projectStatus: ProjectStatus,
                                  version: Option[Int],
                                  order: Option[Int],
                                  creatorId: Long,
                                  updaterId: Long,
                                  createDateTime: LocalDateTime = LocalDateTime.now(),
                                  updateDateTime: LocalDateTime = LocalDateTime.now()
                          ) extends DBTableRow {
  override def toString: String = s"ProjectTableRow(id=$id, projectName=$projectName, description=$description, startDateTime=$startDateTime, endDateTime=$endDateTime, projectStatus=$projectStatus, version=$version, order=$order, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toPayload: ProjectPayload = ProjectPayload(
    id,
    projectName,
    projectStatus,
    description,
    startDateTime,
    endDateTime,
    version,
    order
  )
}
