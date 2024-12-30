package com.advantest.demeter.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.database.table.employee.{EmployeeDBTable, EmployeeDBTableRow}
import com.advantest.demeter.http.payload.EmployeePayload
import com.advantest.demeter.integration.antdesign.select.{LongValue, SelectOption}

import java.time.LocalDateTime
import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
case class EmployeeService() extends Service {
  private val employeeTable: EmployeeDBTable = EmployeeDBTable()

  def checkIfAdmin(employeeId: Long): Boolean = employeeId == EmployeePayload.SystemAdminId

  def register(employee: EmployeePayload): Future[EmployeePayload] = employeeTable.insert(EmployeePayload.create(employee.id, employee)).map(_.toPayload)

  def register(employeeId: Long, users: Seq[EmployeePayload]): Future[Seq[EmployeePayload]] = {
    if (checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can register multiple employees.")
    val tableRows = users.map(user => EmployeePayload.create(user.id, user))
    employeeTable.batchInsert(tableRows).map(_.map(_.toPayload))
  }

  def verifyPassword(account: String, password: String): Future[EmployeePayload] = {
    employeeTable.queryByAccount(account).map {
      case Some(employee: EmployeeDBTableRow) =>
        val isCorrect = employee.password == password
        if (isCorrect) {
          employee.toPayload
        } else throw new IllegalArgumentException(s"Password for account '$account' is incorrect.")
      case None => throw new NoSuchElementException(s"Account '$account' does not exist.")
    }
  }

  def resetPassword(employeeId: Long, oldPassword: String, newPassword: String): Future[EmployeePayload] = {
    employeeTable.queryById(employeeId).flatMap {
      case Some(employee: EmployeeDBTableRow) =>
        val isCorrect = employee.password == oldPassword
        val newEmployee = employee.copy(password = newPassword, updaterId = employeeId, updateDateTime = LocalDateTime.now())
        if (isCorrect) employeeTable.update(newEmployee).map(_.toPayload) else throw new IllegalArgumentException(s"Old password for employee '$employeeId' is incorrect.")
      case None => throw new NoSuchElementException(s"User with ID '$employeeId' does not exist.")
    }
  }

  def getEmployeeSelectOptions(employeeName: String): Future[Seq[SelectOption]] = {
    employeeTable.queryByEmployeeNameLike(employeeName).map(employees => employees.map(employee => SelectOption(employee.employeeName, LongValue(employee.id))))
  }
}
