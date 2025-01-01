package com.advantest.demeter.database.table.employee

import com.advantest.demeter.database.DBTableRow
import com.advantest.demeter.http.payload.EmployeePayload

import java.time.LocalDateTime

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeDBTableRow(
                                   id: Long,
                                   account: String,
                                   password: String,
                                   employeeName: String,
                                   email: String,
                                   phone: Option[String],
                                   isActive: Boolean,
                                   creatorId: Long,
                                   updaterId: Long,
                                   createDateTime: LocalDateTime = LocalDateTime.now(),
                                   updateDateTime: LocalDateTime = LocalDateTime.now()
                             ) extends DBTableRow {

  override def toString: String = s"UserTableRow(id=$id, account=$account, password=$password, employeeName=$employeeName, email=$email, phone=$phone, isActive=$isActive, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toPayload: EmployeePayload = EmployeePayload(
    id = id,
    account = account,
    password = password,
    employeeName = employeeName,
    email = email,
    phone = phone,
    isActive = isActive
  )
}