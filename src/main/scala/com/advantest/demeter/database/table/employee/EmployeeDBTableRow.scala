package com.advantest.demeter.database.table.employee

import com.advantest.demeter.database._
import com.advantest.demeter.http.payload.EmployeePayload

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeDBTableRow(
                                     id: DBLongValue,
                                     account: DBVarcharValue,
                                     password: DBVarcharValue,
                                     employeeName: DBVarcharValue,
                                     email: DBVarcharValue,
                                     phone: Option[DBVarcharValue],
                                     isActive: DBBooleanValue,
                                     creatorId: DBLongValue,
                                     updaterId: DBLongValue,
                                     createDateTime: DBDateTimeValue = DBDateTimeValue.now(),
                                     updateDateTime: DBDateTimeValue = DBDateTimeValue.now()
                                   ) extends DBTableRow {

  def toPayload: EmployeePayload = EmployeePayload(
    id = id.value,
    account = account.value,
    password = password.value,
    employeeName = employeeName.value,
    email = email.value,
    phone = phone.map(_.value),
    isActive = isActive.value
  )
}
