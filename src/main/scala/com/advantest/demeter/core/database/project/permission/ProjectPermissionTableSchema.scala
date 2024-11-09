package com.advantest.demeter.core.database.project.permission

import com.advantest.demeter.core.constant.project.permission.{ProjectPermission, ProjectPermissionCategory}
import com.advantest.demeter.core.database.project.{ProjectTableRow, ProjectTableSchema}
import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import java.time.LocalDateTime

/**
 * Create on 2024/10/30
 * Author: mengen.dai@outlook.com
 */
final class ProjectPermissionTableSchema(tag: Tag) extends Table[ProjectPermissionTableRow](tag, "PROJECT_PERMISSION_TABLE") with DBTableSchema {

  private type Permission = ProjectPermission

  private type Category = ProjectPermissionCategory

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def category: Rep[Category] = column[Category]("CATEGORY")

  def permission: Rep[Permission] = column[Permission]("PERMISSION")

  def employeeId: Rep[Long] = column[Long]("EMPLOYEE_ID")

  def projectId: Rep[Long] = column[Long]("PROJECT_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[ProjectPermissionTableRow] = (
    id,
    category,
    permission,
    employeeId,
    projectId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((ProjectPermissionTableRow.apply _).tupled, ProjectPermissionTableRow.unapply)
}
