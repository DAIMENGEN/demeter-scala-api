package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.core.entity.EmployeeEntity
import com.advantest.demeter.core.service.EmployeeService
import com.advantest.demeter.utils.http.{ApiRequest, ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
case class EmployeeRoute() extends HttpRoute with ApiRequest with ApiResponse {
  private val employeeService = EmployeeService()

  override def route: Route = concat(
    loginRoute,
    logoutRoute,
    registerRoute,
    batchRegisterRoute,
    resetPasswordRoute,
    getUsernameRoute,
  )

  private def loginRoute: Route = path("loginRoute") {
    post {
      entity(as[HttpRequestParams]) {
        request =>
          val account = request.readString("account")
          val password = request.readString("password")
          val future = employeeService.verifyPassword(account, password).map(generateToken)
          response(future)
      }
    }
  }

  private def logoutRoute: Route = path("logoutRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) { request =>
          val account = request.readString("account")
          val account_ = employee.account
          if (account == account_) {
            response(Future("Logout success"))
          } else {
            response(Future("Logout failure"))
          }
        }
      }
    }
  }

  private def registerRoute: Route = path("registerRoute") {
    post {
      entity(as[EmployeeEntity]) {
        request =>
          val future = employeeService.register(request);
          response(future)
      }
    }
  }

  private def batchRegisterRoute: Route = path("batchRegisterRoute") {
    post {
      validateToken { employee =>
        entity(as[List[EmployeeEntity]]) {
          request =>
            val future = employeeService.register(employee.id, request);
            response(future)
        }
      }
    }
  }

  private def resetPasswordRoute: Route = path("resetPasswordRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val oldPassword = request.readString("oldPassword")
            val newPassword = request.readString("newPassword")
            val future = employeeService.resetPassword(employee.id, oldPassword, newPassword)
            response(future)
        }
      }
    }
  }

  private def getUsernameRoute: Route = path("getUsernameRoute") {
    get {
      validateToken { employee =>
        val username = employee.username
        response(Future(username))
      }
    }
  }
}
