package com.advantest.demeter.core.database.mapping.employee_team

import com.advantest.demeter.core.database.employee.{EmployeeTableRow, EmployeeTableSchema}
import com.advantest.demeter.core.database.team.{TeamTableRow, TeamTableSchema}
import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
class EmployeeTeamMappingTableSchema(tag: Tag) extends Table[EmployeeTeamMappingTableRow](tag, "EMPLOYEE_TEAM_MAPPING") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def employeeId: Rep[Long] = column[Long]("EMPLOYEE_ID")

  def teamId: Rep[Long] = column[Long]("TEAM_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  def employeeFk: ForeignKeyQuery[EmployeeTableSchema, EmployeeTableRow] = foreignKey("EMPLOYEE_FK", employeeId, TableQuery[EmployeeTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  def teamFk: ForeignKeyQuery[TeamTableSchema, TeamTableRow] = foreignKey("TEAM_FK", teamId, TableQuery[TeamTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  override def * : ProvenShape[EmployeeTeamMappingTableRow] = (
    id,
    employeeId,
    teamId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((EmployeeTeamMappingTableRow.apply _).tupled, EmployeeTeamMappingTableRow.unapply)
}
