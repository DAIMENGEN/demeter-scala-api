package com.advantest.demeter.http.payload

import com.advantest.demeter.database.table.employee.EmployeeDBTableRow
import com.advantest.demeter.database._
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

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
    id = DBLongValue(payloadData.id),
    account = DBVarcharValue(payloadData.account),
    password = DBVarcharValue(payloadData.password),
    employeeName = DBVarcharValue(payloadData.employeeName),
    email = DBVarcharValue(payloadData.email),
    phone = payloadData.phone.map(p => DBVarcharValue(p)),
    isActive = DBBooleanValue(payloadData.isActive),
    creatorId = DBLongValue(employeeId),
    updaterId = DBLongValue(employeeId),
  )

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    account = DBVarcharValue(payloadData.account),
    password = DBVarcharValue(payloadData.password),
    employeeName = DBVarcharValue(payloadData.employeeName),
    email = DBVarcharValue(payloadData.email),
    phone = payloadData.phone.map(p => DBVarcharValue(p)),
    isActive = DBBooleanValue(payloadData.isActive),
    updaterId = DBLongValue(employeeId),
    updateDateTime = DBDateTimeValue.now()
  )

  val SystemAdminId: Long = 1
}
