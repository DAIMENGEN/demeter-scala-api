package com.advantest.demeter.core.database.project.task

import com.advantest.demeter.core.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskTable()(implicit val db: Database) extends DBTable {

  override protected type TableRowData = ProjectTaskTableRow
  override protected val table: TableQuery[ProjectTaskTableSchema] = TableQuery[ProjectTaskTableSchema]
  createTableIfNotExists()

  def queryByName(name: String): Future[Option[TableRowData]] = {
    val select = table.filter(_.name === name).result.headOption
    db.run(select)
  }

  def queryByNameLike(namePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.name like s"%$namePattern%").result
    db.run(select)
  }

  def queryByDescriptionLike(descriptionPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.description like s"%$descriptionPattern%").result
    db.run(select)
  }

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }

  def queryByParentId(parentId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.parentId === parentId).result
    db.run(select)
  }

  def queryByTaskType(taskType: ProjectTaskType): Future[Seq[TableRowData]] = {
    val select = table.filter(_.taskType === taskType).result
    db.run(select)
  }

  def queryByTaskStatus(taskStatus: ProjectTaskStatus): Future[Seq[TableRowData]] = {
    val select = table.filter(_.taskStatus === taskStatus).result
    db.run(select)
  }
}