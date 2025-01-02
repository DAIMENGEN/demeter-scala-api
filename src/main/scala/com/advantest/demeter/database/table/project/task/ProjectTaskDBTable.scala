package com.advantest.demeter.database.table.project.task

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBLongValue, DBTable}

import scala.concurrent.Future

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectTaskDBTableRow
  override protected val table: TableQuery[ProjectTaskDBTableSchema] = TableQuery[ProjectTaskDBTableSchema]
  createTableIfNotExists()

  def queryByProjectId(projectId: Long): Future[Seq[ProjectTaskDBTableRow]] = {
    val select = table.filter(_.projectId === DBLongValue(projectId)).result
    db.run(select)
  }
}
