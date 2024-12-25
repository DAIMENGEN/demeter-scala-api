package com.advantest.demeter.core.http.payload

import com.advantest.demeter.core.database.employee.EmployeeDBTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.http.HttpPayload
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final case class EmployeePayload(
                                 id: Long,
                                 account: String,
                                 password: String,
                                 employeeName: String,
                                 email: String,
                                 phone: Option[String],
                                 isActive: Boolean
                               ) extends HttpPayload {
  override def toString: String = s"EmployeePayload(id=$id, account=$account, password=$password, employeeName=$employeeName, email=$email, phone=$phone)"
}

object EmployeePayload extends Serializable[EmployeePayload] with DBTableRowFactory {
  override protected type PayloadData = EmployeePayload
  override protected type DBTableRowData = EmployeeDBTableRow

  override implicit val serializeFormat: RootJsonFormat[PayloadData] = jsonFormat7(EmployeePayload.apply)

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData = EmployeeDBTableRow(
    id = payloadData.id,
    account = payloadData.account,
    password = payloadData.password,
    employeeName = payloadData.employeeName,
    email = payloadData.email,
    phone = payloadData.phone,
    isActive = payloadData.isActive,
    creatorId = employeeId,
    updaterId = employeeId,
  )

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    account = payloadData.account,
    password = payloadData.password,
    employeeName = payloadData.employeeName,
    email = payloadData.email,
    phone = payloadData.phone,
    isActive = payloadData.isActive,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )

  val SystemAdminId: Long = 1
}
