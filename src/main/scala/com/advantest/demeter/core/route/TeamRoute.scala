package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.core.service.TeamService
import com.advantest.demeter.utils.http.{ApiRequest, ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class TeamRoute() extends HttpRoute with ApiRequest with ApiResponse {
  private val teamService: TeamService = TeamService()

  override def route: Route = concat(
    getAllTeamsRoute
  )

  private def getAllTeamsRoute: Route = path("getAllTeamsRoute") {
    get {
      val future = teamService.getAllTeams
      response(future)
    }
  }
}
