package com.advantest.demeter.core.entity.project.permission

import com.advantest.demeter.core.constant.project.permission.{ProjectPermission => Permission, ProjectPermissionCategory => Category}
import com.advantest.demeter.core.database.project.permission.ProjectPermissionTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/30
 * Author: mengen.dai@outlook.com
 */
final case class ProjectPermissionEntity(
                                          id: Long,
                                          category: Category,
                                          permission: Permission,
                                          employeeId: Long,
                                        ) {
  override def toString: String = s"ProjectPermissionEntity(id=$id, category=$category, permission=$permission, employeeId=$employeeId)"
}

object ProjectPermissionEntity extends Serializable[ProjectPermissionEntity] with DBTableRowFactory {
  override protected type EntityData = ProjectPermissionEntity
  override protected type TableRowData = ProjectPermissionTableRow

  override implicit val serializeFormat: RootJsonFormat[ProjectPermissionEntity] = jsonFormat4(ProjectPermissionEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = {
    options.flatMap(_.get("projectId").map(_.asInstanceOf[Long])) match {
      case Some(projectId) =>
        ProjectPermissionTableRow(
          id = entityData.id,
          category = entityData.category,
          permission = entityData.permission,
          employeeId = entityData.employeeId,
          projectId = projectId,
          creatorId = employeeId,
          updaterId = employeeId
        )
      case None => throw new IllegalArgumentException("projectId is required when creating a ProjectPermissionTableRow")
    }
  }

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    category = entityData.category,
    permission = entityData.permission,
    employeeId = entityData.employeeId,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}
