package com.advantest.demeter.core.database.project.task.attribute

import com.advantest.demeter.utils.database._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectTaskAttributeDBTableRow
  override protected val table: TableQuery[ProjectTaskAttributeDBTableSchema] = TableQuery[ProjectTaskAttributeDBTableSchema]
  createTableIfNotExists()

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }
}
