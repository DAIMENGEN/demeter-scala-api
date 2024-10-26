package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.core.service.DepartmentService
import com.advantest.demeter.utils.http.{ApiRequest, ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class DepartmentRoute() extends HttpRoute with ApiRequest with ApiResponse {
  private val departmentService: DepartmentService = DepartmentService()

  override def route: Route = concat(
    getAllDepartmentsRoute
  )

  private def getAllDepartmentsRoute: Route = path("getAllDepartmentsRoute") {
    get {
      val future = departmentService.getAllDepartments
      response(future)
    }
  }
}

