package com.advantest.demeter.database.table.project.color

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.{DBLongValue, DBTable, DBVarcharValue}

import scala.concurrent.Future

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final case class ProjectColorDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = ProjectColorDBTableRow
  override protected val table: TableQuery[ProjectColorDBTableSchema] = TableQuery[ProjectColorDBTableSchema]
  createTableIfNotExists()


  def queryByValue(value: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.value === DBVarcharValue(value)).result
    db.run(select)
  }

  def queryByFieldName(fieldName: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.fieldName === DBVarcharValue(fieldName)).result
    db.run(select)
  }

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === DBLongValue(projectId)).result
    db.run(select)
  }

  def queryByDescriptionLike(descriptionPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.description.asColumnOf[Option[String]] like s"%$descriptionPattern%").result
    db.run(select)
  }
}
