package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.database.team.TeamTable
import com.advantest.demeter.core.entity.TeamEntity

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class TeamService() extends Service {
  private val teamTable: TeamTable = TeamTable()

  def getAllTeams: Future[Seq[TeamEntity]] = teamTable.query().map(_.map(_.toEntity))

}
