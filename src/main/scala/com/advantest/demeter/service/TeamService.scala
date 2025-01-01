package com.advantest.demeter.service

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.database.table.team.TeamDBTable
import com.advantest.demeter.http.payload.TeamPayload

import scala.concurrent.Future

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
case class TeamService()(implicit val db: Database) extends Service {
  private val teamTable: TeamDBTable = TeamDBTable()

  def getAllTeams: Future[Seq[TeamPayload]] = teamTable.query().map(_.map(_.toEntity))

}
