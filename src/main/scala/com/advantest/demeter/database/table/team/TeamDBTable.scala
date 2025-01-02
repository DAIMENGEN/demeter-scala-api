package com.advantest.demeter.database.table.team

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBTable, DBVarcharValue}

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class TeamDBTable()(implicit val db: Database) extends DBTable {

  override protected type TableRowData = TeamDBTableRow
  override protected val table: TableQuery[TeamDBTableSchema] = TableQuery[TeamDBTableSchema]
  createTableIfNotExists()

  def queryByName(name: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.name === DBVarcharValue(name)).result.headOption
    db.run(select)
  }

  def queryByNameLike(namePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.name.asColumnOf[String] like s"%$namePattern%").result
    db.run(select)
  }
}
