package com.advantest.demeter.database.table.project.color

import com.advantest.demeter.database._

/**
 * Create on 2024/10/28
 * Author: mengen.dai@outlook.com
 */
final case class ProjectColorDBTableRow(
                                         id: DBLongValue,
                                         value: DBVarcharValue,
                                         description: Option[DBTextValue],
                                         fieldName: DBVarcharValue,
                                         fieldValue: DBVarcharValue,
                                         order: Option[DBIntValue],
                                         projectId: DBLongValue,
                                         creatorId: DBLongValue,
                                         updaterId: DBLongValue,
                                         createDateTime: DBDateTimeValue = DBDateTimeValue.now(),
                                         updateDateTime: DBDateTimeValue = DBDateTimeValue.now()
                                       ) extends DBTableRow {
}
