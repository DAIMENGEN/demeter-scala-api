package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.database.department.DepartmentTable
import com.advantest.demeter.core.entity.DepartmentEntity

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class DepartmentService() extends Service {
  private val departmentTable: DepartmentTable = DepartmentTable()

  def getAllDepartments: Future[Seq[DepartmentEntity]] = departmentTable.query().map(_.map(_.toEntity))

}
