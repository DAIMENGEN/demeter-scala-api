package com.advantest.demeter.core.database

import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
class DepartmentTable(implicit val db: Database) extends DBTable {

  override protected type TableRowData = DepartmentTableRow
  override protected val table: TableQuery[DepartmentTableSchema] = TableQuery[DepartmentTableSchema]
  createTableIfNotExists()

  def queryByName(name: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.name === name).result.headOption
    db.run(select)
  }

  def queryByNameLike(namePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.name like s"%$namePattern%").result
    db.run(select)
  }
}
