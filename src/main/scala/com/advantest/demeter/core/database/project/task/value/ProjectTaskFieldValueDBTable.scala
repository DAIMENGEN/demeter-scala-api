package com.advantest.demeter.core.database.project.task.value

import com.advantest.demeter.utils.database._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */

trait ProjectTaskFieldValueDBTable extends DBTable {
  protected type TableRowData = ProjectTaskFieldValueDBTableRow
  protected val table: TableQuery[_ <: ProjectTaskFieldValueDBTableSchema[TableRowData]]

  def queryByProjectId(projectId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.projectId === projectId).result
    db.run(select)
  }
}

final case class ProjectTaskFieldBooleanTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable() {
  override protected val table: TableQuery[ProjectTaskFieldBooleanTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldBooleanTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldDatetimeTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldDatetimeTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldDatetimeTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldDateTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldDateTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldDateTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldDoubleTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldDoubleTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldDoubleTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldFloatTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldFloatTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldFloatTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldIntTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldIntTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldIntTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldJsonTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldJsonTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldJsonTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldLongTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldLongTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldLongTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldStringTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldStringTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldStringTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldTextTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldTextTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldTextTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldMediumtextTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldMediumtextTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldMediumtextTypeValueDBTableSchema]
  createTableIfNotExists()
}

final case class ProjectTaskFieldLongtextTypeValueDBTable()(implicit val db: Database) extends ProjectTaskFieldValueDBTable {
  override protected val table: TableQuery[ProjectTaskFieldLongtextTypeValueDBTableSchema] = TableQuery[ProjectTaskFieldLongtextTypeValueDBTableSchema]
  createTableIfNotExists()
}


