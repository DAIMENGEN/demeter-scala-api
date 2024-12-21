package com.advantest.demeter.core.database.project.task

import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskDBTableRow(
                                        id: Long,
                                        taskName: String,
                                        order: Option[Int],
                                        projectId: Long,
                                        creatorId: Long,
                                        updaterId: Long,
                                        createDateTime: LocalDateTime = LocalDateTime.now(),
                                        updateDateTime: LocalDateTime = LocalDateTime.now()
                                      ) extends DBTableRow {
  override def toString: String = s"ProjectTaskDBTableRow(id=$id, taskName=$taskName, order=$order, projectId=$projectId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}
