package com.advantest.demeter.database.table.project.task.value

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.DBTable

import scala.concurrent.Future

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */

trait ProjectTaskAttributeValueDBTable extends DBTable {
  protected type TableRowData = ProjectTaskAttributeValueDBTableRow
  protected val table: TableQuery[_ <: ProjectTaskAttributeValueDBTableSchema[TableRowData]]

  def queryByTaskId(taskId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.taskId === taskId).result
    db.run(select)
  }

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }
}

final case class ProjectTaskBooleanTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable() {
  override protected val table: TableQuery[ProjectTaskBooleanTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskBooleanTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskDatetimeTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskDatetimeTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskDatetimeTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskDateTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskDateTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskDateTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskDoubleTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskDoubleTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskDoubleTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFloatTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskFloatTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskFloatTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskIntTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskIntTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskIntTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskJsonTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskJsonTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskJsonTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskLongTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskLongTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskLongTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskStringTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskStringTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskStringTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskTextTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskTextTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskTextTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskMediumtextTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskMediumtextTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskMediumtextTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskLongtextTypeAttributeValueDBTable()(implicit val db: Database) extends ProjectTaskAttributeValueDBTable {
  override protected val table: TableQuery[ProjectTaskLongtextTypeAttributeValueDBTableSchema] = TableQuery[ProjectTaskLongtextTypeAttributeValueDBTableSchema]
  createTableIfNotExists()
}


