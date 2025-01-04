package com.advantest.demeter.database.table.project.task

import com.advantest.demeter.database._

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 */
final case class ProjectTaskDBTableRow(
                                        id: DBLongValue,
                                        taskName: DBVarcharValue,
                                        projectId: DBLongValue,
                                        creatorId: DBLongValue,
                                        updaterId: DBLongValue,
                                        parentTaskId: Option[DBLongValue] = None,
                                        createDateTime: DBDateTimeValue = DBDateTimeValue.now(),
                                        updateDateTime: DBDateTimeValue = DBDateTimeValue.now()
                                      ) extends DBTableRow {
}
