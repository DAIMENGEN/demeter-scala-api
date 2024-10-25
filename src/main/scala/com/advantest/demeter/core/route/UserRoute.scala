package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.core.entity.UserEntity
import com.advantest.demeter.core.service.UserService
import com.advantest.demeter.utils.http.{ApiRequest, ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
case class UserRoute() extends HttpRoute with ApiRequest with ApiResponse {
  private val userService = UserService()

  override def route: Route = concat(
    loginRoute,
    logoutRoute,
    registerRoute,
    batchRegisterRoute,
    resetPasswordRoute,
  )

  private def loginRoute: Route = path("loginRoute") {
    post {
      entity(as[HttpRequestParams]) {
        request =>
          val account = request.readString("account")
          val password = request.readString("password")
          val future = userService.verifyPassword(account, password)
          response(future)
      }
    }
  }

  private def logoutRoute: Route = path("logoutRoute") {
    post {
      invalidate {
        response(Future("Logout success"))
      }
    }
  }

  private def registerRoute: Route = path("registerRoute") {
    post {
      entity(as[UserEntity]) {
        request =>
          val future = userService.register(request)
          response(future)
      }
    }
  }

  private def batchRegisterRoute: Route = path("batchRegisterRoute") {
    post {
      required { userId =>
        entity(as[List[UserEntity]]) {
          request =>
            val future = userService.register(userId, request)
            response(future)
        }
      }
    }
  }

  private def resetPasswordRoute: Route = path("resetPasswordRoute") {
    post {
      required { userId =>
        entity(as[HttpRequestParams]) {
          request =>
            val oldPassword = request.readString("oldPassword")
            val newPassword = request.readString("newPassword")
            val future = userService.resetPassword(userId, oldPassword, newPassword)
            response(future)
        }
      }
    }
  }
}
