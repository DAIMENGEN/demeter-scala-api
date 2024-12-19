package com.advantest.demeter.core.database.project.task.field

import com.advantest.demeter.utils.database._

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskFieldDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectTaskFieldDBTableRow
  override protected val table: TableQuery[ProjectTaskFieldDBTableSchema] = TableQuery[ProjectTaskFieldDBTableSchema]
  createTableIfNotExists()

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }
}
