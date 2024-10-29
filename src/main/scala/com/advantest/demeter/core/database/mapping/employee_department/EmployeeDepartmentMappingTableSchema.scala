package com.advantest.demeter.core.database.mapping.employee_department

import com.advantest.demeter.core.database.department.{DepartmentTableRow, DepartmentTableSchema}
import com.advantest.demeter.core.database.employee.{EmployeeTableRow, EmployeeTableSchema}
import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final class EmployeeDepartmentMappingTableSchema(tag: Tag) extends Table[EmployeeDepartmentMappingTableRow](tag, "EMPLOYEE_DEPARTMENT_MAPPING") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def employeeId: Rep[Long] = column[Long]("EMPLOYEE_ID")

  def departmentId: Rep[Long] = column[Long]("DEPARTMENT_ID")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  def employeeIdFk: ForeignKeyQuery[EmployeeTableSchema, EmployeeTableRow] = foreignKey("EMPLOYEE_ID_FK", employeeId, TableQuery[EmployeeTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  def departmentIdFk: ForeignKeyQuery[DepartmentTableSchema, DepartmentTableRow] = foreignKey("DEPARTMENT_ID_FK", departmentId, TableQuery[DepartmentTableSchema])(_.id, onDelete = ForeignKeyAction.Cascade)

  override def * : ProvenShape[EmployeeDepartmentMappingTableRow] = (
    id,
    employeeId,
    departmentId,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((EmployeeDepartmentMappingTableRow.apply _).tupled, EmployeeDepartmentMappingTableRow.unapply)
}
