package com.advantest.demeter.core.database.project.permission

import com.advantest.demeter.core.constant.project.permission.{ProjectPermission, ProjectPermissionCategory}
import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/30
 * Author: mengen.dai@outlook.com
 */
final case class ProjectPermissionTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectPermissionTableRow
  override protected val table: TableQuery[ProjectPermissionTableSchema] = TableQuery[ProjectPermissionTableSchema]
  createTableIfNotExists()

  private type Permission = ProjectPermission
  private type Category = ProjectPermissionCategory

  def queryByCategory(category: Category): Future[Seq[TableRowData]] = {
    val select = table.filter(_.category === category)
    db.run(select.result)
  }

  def queryByPermission(permission: Permission): Future[Seq[TableRowData]] = {
    val select = table.filter(_.permission === permission)
    db.run(select.result)
  }

  def queryByEmployeeId(employeeId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeId === employeeId)
    db.run(select.result)
  }

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId)
    db.run(select.result)
  }

  def queryByEmployeeIdAndProjectId(employeeId: Long, projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeId === employeeId).filter(_.projectId === projectId)
    db.run(select.result)
  }

  def queryByEmployeeIdAndProjectIds(employeeId: Long, projectIds: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeId === employeeId).filter(_.projectId inSet projectIds)
    db.run(select.result)
  }

  def queryByEmployeeIdAndProjectIdAndPermission(employeeId: Long, projectId: Long, permission: Permission): Future[Option[TableRowData]] = {
    val select = table.filter(_.employeeId === employeeId).filter(_.projectId === projectId).filter(_.permission === permission)
    db.run(select.result.headOption)
  }

  def queryByEmployeeIdAndProjectIdAndCategory(employeeId: Long, projectId: Long, category: Category): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeId === employeeId).filter(_.projectId === projectId).filter(_.category === category)
    db.run(select.result)
  }
}
