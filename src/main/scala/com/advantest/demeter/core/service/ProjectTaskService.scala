package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.core.database.project.task.field.ProjectTaskFieldDBTable
import com.advantest.demeter.core.database.project.task.value.{
  ProjectTaskFieldTextTypeValueDBTable => TextTypeValueDBTable,
  ProjectTaskFieldLongtextTypeValueDBTable => LongtextTypeValueDBTable,
  ProjectTaskFieldMediumtextTypeValueDBTable => MediumtextTypeValueDBTable,
  ProjectTaskFieldDatetimeTypeValueDBTable => DatetimeTypeValueDBTable, ProjectTaskFieldBooleanTypeValueDBTable => BooleanTypeValueDBTable, ProjectTaskFieldDateTypeValueDBTable => DateTypeValueDBTable, ProjectTaskFieldDoubleTypeValueDBTable => DoubleTypeValueDBTable, ProjectTaskFieldFloatTypeValueDBTable => FloatTypeValueDBTable, ProjectTaskFieldIntTypeValueDBTable => IntTypeValueDBTable, ProjectTaskFieldJsonTypeValueDBTable => JsonTypeValueDBTable, ProjectTaskFieldLongTypeValueDBTable => LongTypeValueDBTable, ProjectTaskFieldStringTypeValueDBTable => StringTypeValueDBTable}
import com.advantest.demeter.core.entity.project.task.field.ProjectTaskFieldEntity
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/12/20
 * Author: mengen.dai@outlook.com
 */
case class ProjectTaskService()(implicit val db: Database) extends Service {
  private val taskFieldDBTable: ProjectTaskFieldDBTable = ProjectTaskFieldDBTable()
  private val intTypeValueDBTable: IntTypeValueDBTable = IntTypeValueDBTable()
  private val dateTypeValueDBTable: DateTypeValueDBTable = DateTypeValueDBTable()
  private val jsonTypeValueDBTable: JsonTypeValueDBTable = JsonTypeValueDBTable()
  private val longTypeValueDBTable: LongTypeValueDBTable = LongTypeValueDBTable()
  private val floatTypeValueDBTable: FloatTypeValueDBTable = FloatTypeValueDBTable()
  private val doubleTypeValueDBTable: DoubleTypeValueDBTable = DoubleTypeValueDBTable()
  private val stringTypeValueDBTable: StringTypeValueDBTable = StringTypeValueDBTable()
  private val booleanTypeValueDBTable: BooleanTypeValueDBTable = BooleanTypeValueDBTable()
  private val datetimeTypeValueDBTable: DatetimeTypeValueDBTable = DatetimeTypeValueDBTable()
  private val textTypeValueDBTable: TextTypeValueDBTable = TextTypeValueDBTable()
  private val longtextTypeValueDBTable: LongtextTypeValueDBTable = LongtextTypeValueDBTable()
  private val mediumtextTypeValueDBTable: MediumtextTypeValueDBTable = MediumtextTypeValueDBTable()

  def getTaskFieldsByProjectId(projectId: Long): Future[Seq[ProjectTaskFieldEntity]] = {
    taskFieldDBTable.queryByProjectId(projectId).map(_.map(_.toEntity))
  }

}