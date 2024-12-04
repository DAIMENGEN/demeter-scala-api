package com.advantest.demeter.core.database.project.permission

import com.advantest.demeter.core.constant.project.permission.{ProjectPermission => Permission, ProjectPermissionCategory => Category}
import com.advantest.demeter.core.entity.project.permission.ProjectPermissionEntity
import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/30
 * Author: mengen.dai@outlook.com
 */
final case class ProjectPermissionDBTableRow(
                                            id: Long,
                                            category: Category,
                                            permission: Permission,
                                            employeeId: Long,
                                            projectId: Long,
                                            creatorId: Long,
                                            updaterId: Long,
                                            createDateTime: LocalDateTime = LocalDateTime.now(),
                                            updateDateTime: LocalDateTime = LocalDateTime.now()
                                          ) extends DBTableRow {
  override def toString: String = s"ProjectPermissionTableRow(id=$id, category=$category, permission=$permission, employeeId=$employeeId, projectId=$projectId, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: ProjectPermissionEntity = ProjectPermissionEntity(
    id = id,
    category = category,
    permission = permission,
    employeeId = employeeId,
  )
}
