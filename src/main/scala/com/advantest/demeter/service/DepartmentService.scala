package com.advantest.demeter.service

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.database.table.department.DepartmentDBTable
import com.advantest.demeter.http.payload.DepartmentPayload

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class DepartmentService()(implicit val db: Database) extends Service {
  private val departmentTable: DepartmentDBTable = DepartmentDBTable()

  def getAllDepartments: Future[Seq[DepartmentPayload]] = departmentTable.query().map(_.map(_.toEntity))

}
