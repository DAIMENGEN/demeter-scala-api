package com.advantest.demeter.core.database

import com.advantest.demeter.core.entity.UserEntity
import com.advantest.demeter.utils.database.DBTableData
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDateTime

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
class UserTableDescriptor(tag: Tag) extends Table[UserEntity](tag, "USER_TABLE") with DBTableData {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def account: Rep[String] = column[String]("ACCOUNT")

  def password: Rep[String] = column[String]("PASSWORD")

  def username: Rep[String] = column[String]("USERNAME")

  def email: Rep[String] = column[String]("EMAIL")

  def phone: Rep[Option[String]] = column[Option[String]]("PHONE")

  def teamId: Rep[Option[Long]] = column[Option[Long]]("TEAM_ID")

  def departmentId: Rep[Option[Long]] = column[Option[Long]]("DEPARTMENT_ID")

  def isActive: Rep[Boolean] = column[Boolean]("IS_ACTIVE")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[UserEntity] = (
    id,
    account,
    password,
    username,
    email,
    phone,
    teamId,
    departmentId,
    isActive,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((UserEntity.apply _).tupled, UserEntity.unapply)
}
