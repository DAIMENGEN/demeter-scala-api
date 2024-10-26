package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT, DEMETER_SESSION_MANAGER, REFRESH_TOKEN_STORAGE}
import com.advantest.demeter.core.database.user.{UserTable, UserTableRow}
import com.advantest.demeter.core.entity.UserEntity
import com.softwaremill.session.SessionDirectives.setSession
import com.softwaremill.session.SessionOptions.{refreshable, usingCookies}

import java.time.LocalDateTime
import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
case class UserService() extends UserTable with Service {

  def checkIfAdmin(userId: Long): Boolean = userId == UserEntity.SystemAdminId

  def register(user: UserEntity): Future[UserEntity] = insert(UserEntity.create(user.id, user)).map(_.toEntity)

  def register(userId: Long, users: Seq[UserEntity]): Future[Seq[UserEntity]] = {
    if (checkIfAdmin(userId)) throw new IllegalArgumentException("Only system admin can register multiple users.")
    val tableRows = users.map(user => UserEntity.create(user.id, user))
    batchInsert(tableRows).map(_.map(_.toEntity))
  }

  def verifyPassword(account: String, password: String): Future[UserEntity] = {
    queryByAccount(account).map {
      case Some(user: UserTableRow) =>
        val isCorrect = user.password == password
        if (isCorrect) {
          setSession(refreshable, usingCookies, user.id)
          user.toEntity
        } else throw new IllegalArgumentException(s"Password for account '$account' is incorrect.")
      case None => throw new NoSuchElementException(s"Account '$account' does not exist.")
    }
  }

  def resetPassword(userId: Long, oldPassword: String, newPassword: String): Future[UserEntity] = {
    queryById(userId).flatMap {
      case Some(user: UserTableRow) =>
        val isCorrect = user.password == oldPassword
        val newUser = user.copy(password = newPassword, updaterId = userId, updateDateTime = LocalDateTime.now())
        if (isCorrect) update(newUser).map(_.toEntity) else throw new IllegalArgumentException(s"Old password for user '$userId' is incorrect.")
      case None => throw new NoSuchElementException(s"User with ID '$userId' does not exist.")
    }
  }
}
