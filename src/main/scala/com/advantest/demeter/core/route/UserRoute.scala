package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.core.entity.UserEntity
import com.advantest.demeter.core.service.UserService
import com.advantest.demeter.utils.http.{ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
case class UserRoute() extends HttpRoute with ApiResponse {
  private val userService = UserService()

  override def route: Route = concat(
    loginRoute,
    registerRoute,
    batchRegisterRoute,
    updateUserRoute(),
    batchUpdateUserRoute(),
    getAllUsersRoute,
    getUsersByTeamIdRoute,
    getUsersByDepartmentIdRoute
  )

  private def loginRoute: Route = path("loginRoute") {
    post {
      entity(as[HttpRequestParams]) {
        request =>
          val account = request.readString("account")
          val password = request.readString("password")
          val future = userService.verifyPassword(account, password);
          response(future)
      }
    }
  }

  private def registerRoute: Route = path("registerRoute") {
    post {
      entity(as[UserEntity]) {
        request =>
          val future = userService.register(request);
          response(future)
      }
    }
  }

  private def batchRegisterRoute: Route = path("batchRegisterRoute") {
    post {
      entity(as[List[UserEntity]]) {
        request =>
          val future = userService.register(request);
          response(future)
      }
    }
  }

  private def updateUserRoute(): Route = path("updateUserRoute") {
    post {
      entity(as[UserEntity]) {
        request =>
          val future = userService.update(request);
          response(future)
      }
    }
  }

  private def batchUpdateUserRoute(): Route = path("batchUpdateUserRoute") {
    post {
      entity(as[List[UserEntity]]) {
        request =>
          val future = userService.update(request);
          response(future)
      }
    }
  }

  private def getAllUsersRoute: Route = path("getAllUsersRoute") {
    post {
      val future = userService.query()
      response(future)
    }
  }

  private def getUsersByTeamIdRoute: Route = path("getUsersByTeamIdRoute") {
    post {
      entity(as[HttpRequestParams]) {
        request =>
          val teamId = request.readLong("teamId")
          val future = userService.queryByTeamId(teamId)
          response(future)
      }
    }
  }

  private def getUsersByDepartmentIdRoute: Route = path("getUsersByDepartmentIdRoute") {
    post {
      entity(as[HttpRequestParams]) {
        request =>
          val departmentId = request.readLong("departmentId")
          val future = userService.queryByDepartmentId(departmentId)
          response(future)
      }
    }
  }
}
