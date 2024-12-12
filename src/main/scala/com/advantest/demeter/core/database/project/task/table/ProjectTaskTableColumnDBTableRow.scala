package com.advantest.demeter.core.database.project.task.table

import com.advantest.demeter.utils.database.DBTableRow
import com.advantest.demeter.utils.json.JsonObject
import com.advantest.demeter.utils.snowflake.SnowflakeIdUtil

import java.time.LocalDateTime

/**
 * Create on 2024/12/4
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskTableColumnDBTableRow(
                                                   id: Long,
                                                   key: String,
                                                   title: String,
                                                   dataIndex: String,
                                                   minWidth: Option[Int] = None,
                                                   valueType: Option[String] = None,
                                                   description: Option[String] = None,
                                                   filedProps: Option[JsonObject] = None,
                                                   formItemProps: Option[JsonObject] = None,
                                                   projectId: Option[Long],
                                                   order: Int,
                                                   creatorId: Long,
                                                   updaterId: Long,
                                                   createDateTime: LocalDateTime = LocalDateTime.now(),
                                                   updateDateTime: LocalDateTime = LocalDateTime.now()
                                                 ) extends DBTableRow {
  override def toString: String = s"ProjectTaskTableColumnDBTableRow(id=$id, key=$key, title=$title, dataIndex=$dataIndex, minWidth=$minWidth, valueType=$valueType description=$description, filedProps=${filedProps.map(_.toString)}, formItemProps=${formItemProps.map(_.toString)}, projectId=$projectId, order=$order, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}

object ProjectTaskTableColumnDBTableRow {

  def createColumnRow(title: String,
                      dataIndex: String,
                      employeeId: Long,
                      order: Int,
                      minWidth: Option[Int] = None,
                      projectId: Option[Long] = None,
                      valueType: Option[String] = None,
                      filedProps: Option[JsonObject] = None,
                      formItemProps: Option[JsonObject] = None,
                     ): ProjectTaskTableColumnDBTableRow = {
    val id = SnowflakeIdUtil.nextId()
    ProjectTaskTableColumnDBTableRow(
      id,
      id.toString,
      title,
      dataIndex,
      minWidth,
      valueType,
      None,
      filedProps,
      formItemProps,
      projectId,
      order,
      employeeId,
      employeeId
    )
  }
}
