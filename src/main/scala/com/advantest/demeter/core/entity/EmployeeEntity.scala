package com.advantest.demeter.core.entity

import com.advantest.demeter.core.database.employee.EmployeeTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final case class EmployeeEntity(
                             id: Long,
                             account: String,
                             password: String,
                             username: String,
                             email: String,
                             phone: Option[String],
                             isActive: Boolean
                           ) {
  override def toString: String = s"EmployeeEntity(id=$id, account=$account, password=$password, username=$username, email=$email, phone=$phone)"
}

object EmployeeEntity extends Serializable[EmployeeEntity] with DBTableRowFactory {
  override protected type EntityData = EmployeeEntity
  override protected type TableRowData = EmployeeTableRow

  override implicit val serializeFormat: RootJsonFormat[EmployeeEntity] = jsonFormat7(EmployeeEntity.apply)

  override def create(employeeId: Long, entityData: EmployeeEntity): EmployeeTableRow = EmployeeTableRow(
    id = entityData.id,
    account = entityData.account,
    password = entityData.password,
    username = entityData.username,
    email = entityData.email,
    phone = entityData.phone,
    teamId = None, // TODO: teamId and departmentId are not supported yet
    departmentId = None,
    isActive = entityData.isActive,
    creatorId = employeeId,
    updaterId = employeeId,
  )

  override def update(employeeId: Long, entityData: EmployeeEntity, oldRowData: EmployeeTableRow): EmployeeTableRow = oldRowData.copy(
    account = entityData.account,
    password = entityData.password,
    username = entityData.username,
    email = entityData.email,
    phone = entityData.phone,
    teamId = None, // TODO: teamId and departmentId are not supported yet
    departmentId = None,
    isActive = entityData.isActive,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )

  val SystemAdminId: Long = 1
}
