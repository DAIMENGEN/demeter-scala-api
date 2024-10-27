package com.advantest.demeter.core.database.mapping.employee_department

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeDepartmentMappingTable()(implicit val db: Database) extends DBTable {

  override protected type TableRowData = EmployeeDepartmentMappingTableRow
  override protected val table: TableQuery[EmployeeDepartmentMappingTableSchema] = TableQuery[EmployeeDepartmentMappingTableSchema]
  createTableIfNotExists()

  def queryByEmployeeId(employeeId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeId === employeeId).result
    db.run(select)
  }

  def queryByEmployeeIds(employeeIds: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeId.inSet(employeeIds)).result
    db.run(select)
  }

  def queryByDepartmentId(departmentId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.departmentId === departmentId).result
    db.run(select)
  }

  def queryByDepartmentIds(departmentIds: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.departmentId.inSet(departmentIds)).result
    db.run(select)
  }
}
