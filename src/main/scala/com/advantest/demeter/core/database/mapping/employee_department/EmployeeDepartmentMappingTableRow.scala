package com.advantest.demeter.core.database.mapping.employee_department

import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeDepartmentMappingTableRow(
                                               id: Long,
                                               employeeId: Long,
                                               departmentId: Long,
                                               creatorId: Long,
                                               updaterId: Long,
                                               createDateTime: LocalDateTime = LocalDateTime.now(),
                                               updateDateTime: LocalDateTime = LocalDateTime.now()
                                             ) extends DBTableRow {
  override def toString: String = s"EmployeeDepartmentMappingRow(id=$id, employeeId=$employeeId, departmentId=$departmentId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}
