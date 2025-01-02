package com.advantest.demeter.database.table.project

import com.advantest.demeter.constant.project.ProjectStatus
import com.advantest.demeter.database._
import com.advantest.demeter.http.payload.ProjectPayload

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
final case class ProjectDBTableRow(
                                    id: DBLongValue,
                                    projectName: DBVarcharValue,
                                    description: Option[DBTextValue],
                                    startDateTime: DBDateValue,
                                    endDateTime: Option[DBDateValue],
                                    projectStatus: DBIntValue,
                                    version: Option[DBIntValue],
                                    order: Option[DBIntValue],
                                    creatorId: DBLongValue,
                                    updaterId: DBLongValue,
                                    createDateTime: DBDateTimeValue = DBDateTimeValue.now(),
                                    updateDateTime: DBDateTimeValue = DBDateTimeValue.now()
                                  ) extends DBTableRow {

  def toPayload: ProjectPayload = ProjectPayload(
    id.value,
    projectName.value,
    ProjectStatus.fromInt(projectStatus.value),
    description.map(_.value),
    startDateTime.value,
    endDateTime.map(_.value),
    version.map(_.value),
    order.map(_.value)
  )
}
