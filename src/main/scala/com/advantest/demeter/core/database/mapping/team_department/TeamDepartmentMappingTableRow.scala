package com.advantest.demeter.core.database.mapping.team_department

import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class TeamDepartmentMappingTableRow(
                                                id: Long,
                                                teamId: Long,
                                                departmentId: Long,
                                                creatorId: Long,
                                                updaterId: Long,
                                                createDateTime: LocalDateTime = LocalDateTime.now(),
                                                updateDateTime: LocalDateTime = LocalDateTime.now()
                                              ) extends DBTableRow {
  override def toString: String = s"TeamDepartmentMappingTableRow(id=$id, teamId=$teamId, departmentId=$departmentId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}
