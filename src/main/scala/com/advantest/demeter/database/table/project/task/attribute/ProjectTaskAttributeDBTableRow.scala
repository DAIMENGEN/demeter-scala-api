package com.advantest.demeter.database.table.project.task.attribute

import com.advantest.demeter.database._
import com.advantest.demeter.http.payload.ProjectTaskAttributePayload

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskAttributeDBTableRow(
                                                 id: DBLongValue,
                                                 taskAttributeName: DBVarcharValue,
                                                 taskAttributeType: DBFieldType,
                                                 projectId: DBLongValue,
                                                 properties: Option[DBJsonValue] = None, // 存储 column 相关的内容同，比如： fieldProps, formItemProps
                                                 order: DBIntValue,
                                                 creatorId: DBLongValue,
                                                 updaterId: DBLongValue,
                                                 createDateTime: DBDateTimeValue = DBDateTimeValue.now(),
                                                 updateDateTime: DBDateTimeValue = DBDateTimeValue.now()
                                               ) extends DBTableRow {

  def toPayload: ProjectTaskAttributePayload = {
    ProjectTaskAttributePayload(
      id = id.value,
      taskAttributeName = taskAttributeName.value,
      taskAttributeType = taskAttributeType,
      properties = properties.map(_.value),
      order = order.value
    )
  }
}