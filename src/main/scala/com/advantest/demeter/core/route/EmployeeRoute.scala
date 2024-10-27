package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.core.service.EmployeeService
import com.advantest.demeter.utils.http.{ApiRequest, ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
case class EmployeeRoute() extends HttpRoute with ApiRequest with ApiResponse {
  private val userService = EmployeeService()

  override def route: Route = concat(
    loginRoute,
    logoutRoute,
  )

  private def loginRoute: Route = path("loginRoute") {
    post {
      entity(as[HttpRequestParams]) {
        request =>
          val account = request.readString("account")
          val password = request.readString("password")
          val future = userService.verifyPassword(account, password).map(generateToken)
          response(future)
      }
    }
  }

  private def logoutRoute: Route = path("logoutRoute") {
    post {
      response(Future("Logout success"))
    }
  }
}
