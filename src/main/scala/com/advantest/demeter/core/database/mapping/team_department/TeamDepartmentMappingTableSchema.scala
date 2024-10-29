package com.advantest.demeter.core.database.mapping.team_department

import com.advantest.demeter.core.database.department.{DepartmentTableRow, DepartmentTableSchema}
import com.advantest.demeter.core.database.team.{TeamTableRow, TeamTableSchema}
import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final class TeamDepartmentMappingTableSchema(tag: Tag) extends Table[TeamDepartmentMappingTableRow](tag, "TEAM_DEPARTMENT_MAPPING") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def teamId: Rep[Long] = column[Long]("TEAM_ID")

  def departmentId: Rep[Long] = column[Long]("DEPARTMENT_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  def departmentIdFk: ForeignKeyQuery[DepartmentTableSchema, DepartmentTableRow] = foreignKey("DEPARTMENT_ID_FK", departmentId, TableQuery[DepartmentTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  def teamIdFk: ForeignKeyQuery[TeamTableSchema, TeamTableRow] = foreignKey("TEAM_ID_FK", teamId, TableQuery[TeamTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  override def * : ProvenShape[TeamDepartmentMappingTableRow] = (
    id,
    teamId,
    departmentId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((TeamDepartmentMappingTableRow.apply _).tupled, TeamDepartmentMappingTableRow.unapply)
}
