package com.advantest.demeter.core.database.mapping.employee_team

import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeTeamMappingTableRow(
                                              id: Long,
                                              employeeId: Long,
                                              teamId: Long,
                                              creatorId: Long,
                                              updaterId: Long,
                                              createDateTime: LocalDateTime = LocalDateTime.now(),
                                              updateDateTime: LocalDateTime = LocalDateTime.now()
                                            ) extends DBTableRow {
  override def toString: String = s"EmployeeTeamMappingTableRow(id=$id, employeeId=$employeeId, teamId=$teamId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}
