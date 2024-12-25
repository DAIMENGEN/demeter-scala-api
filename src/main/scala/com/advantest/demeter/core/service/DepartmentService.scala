package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.database.department.DepartmentDBTable
import com.advantest.demeter.core.http.payload.DepartmentPayload

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class DepartmentService() extends Service {
  private val departmentTable: DepartmentDBTable = DepartmentDBTable()

  def getAllDepartments: Future[Seq[DepartmentPayload]] = departmentTable.query().map(_.map(_.toEntity))

}
