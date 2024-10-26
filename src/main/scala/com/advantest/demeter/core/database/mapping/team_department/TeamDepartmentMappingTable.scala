package com.advantest.demeter.core.database.mapping.team_department

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
class TeamDepartmentMappingTable(implicit val db: Database) extends DBTable {

  override protected type TableRowData = TeamDepartmentMappingTableRow
  override protected val table: TableQuery[TeamDepartmentMappingTableSchema] = TableQuery[TeamDepartmentMappingTableSchema]
  createTableIfNotExists()

  def queryByTeamId(teamId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.teamId === teamId).result
    db.run(select)
  }

  def queryByTeamIds(teamIds: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.teamId.inSet(teamIds)).result
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
