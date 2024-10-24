package com.advantest.demeter.core.database

import com.advantest.demeter.core.entity.UserEntity
import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
class UserTable(implicit val db: Database) extends DBTable {

  override protected type EntityData = UserEntity
  override protected val table: TableQuery[UserTableDescriptor] = TableQuery[UserTableDescriptor]
  createTableIfNotExists()

  def queryByAccount(account: String): Future[Option[UserEntity]] = {
    val select = table.filter(_.account === account).result.headOption
    db.run(select)
  }

  def queryByEmail(email: String): Future[Option[UserEntity]] = {
    val select = table.filter(_.email === email).result.headOption
    db.run(select)
  }

  def queryByPhone(phone: String): Future[Option[UserEntity]] = {
    val select = table.filter(_.phone === phone).result.headOption
    db.run(select)
  }

  def queryByTeamId(teamId: Long): Future[Seq[UserEntity]] = {
    val select = table.filter(_.teamId === teamId).result
    db.run(select)
  }

  def queryByDepartmentId(departmentId: Long): Future[Seq[UserEntity]] = {
    val select = table.filter(_.departmentId === departmentId).result
    db.run(select)
  }
}
