package com.advantest.demeter.core.database.mapping.employee_team

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
class EmployeeTeamMappingTable(implicit val db: Database) extends DBTable {

  override protected type TableRowData = EmployeeTeamMappingTableRow
  override protected val table: TableQuery[EmployeeTeamMappingTableSchema] = TableQuery[EmployeeTeamMappingTableSchema]
  createTableIfNotExists()

  def queryByEmployeeId(employeeId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.employeeId === employeeId).result
    db.run(select)
  }

  def queryByTeamId(teamId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.teamId === teamId).result
    db.run(select)
  }
}
