package com.advantest.demeter.http.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.http.{ApiRequest, ApiResponse, HttpRoute}
import com.advantest.demeter.service.TeamService
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
