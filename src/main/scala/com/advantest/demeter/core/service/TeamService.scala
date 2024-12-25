package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.database.team.TeamDBTable
import com.advantest.demeter.core.http.payload.TeamPayload

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class TeamService() extends Service {
  private val teamTable: TeamDBTable = TeamDBTable()

  def getAllTeams: Future[Seq[TeamPayload]] = teamTable.query().map(_.map(_.toEntity))

}
