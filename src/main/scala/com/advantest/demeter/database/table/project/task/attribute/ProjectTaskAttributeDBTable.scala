package com.advantest.demeter.database.table.project.task.attribute

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBLongValue, DBTable}

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectTaskAttributeDBTableRow
  override protected val table: TableQuery[ProjectTaskAttributeDBTableSchema] = TableQuery[ProjectTaskAttributeDBTableSchema]

  override def tableQuery: TableQuery[ProjectTaskAttributeDBTableSchema] = table

  createTableIfNotExists()

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === DBLongValue(projectId)).result
    db.run(select)
  }
}
