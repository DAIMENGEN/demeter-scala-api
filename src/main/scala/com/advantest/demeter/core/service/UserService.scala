package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.database.UserTable
import com.advantest.demeter.core.entity.UserEntity

import scala.concurrent.Future

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
case class UserService() extends UserTable with Service {

  def register(user: UserEntity): Future[UserEntity] = insert(user)

  def register(users: Seq[UserEntity]): Future[Seq[UserEntity]] = batchInsert(users)

  def verifyPassword(account: String, password: String): Future[UserEntity] = {
    queryByAccount(account).map {
      case Some(user: UserEntity) =>
        val isCorrect = user.password == password
        if (isCorrect) user else throw new IllegalArgumentException(s"Password for account '$account' is incorrect.")
      case None => throw new NoSuchElementException(s"Account '$account' does not exist.")
    }
  }

  def resetPassword(userId: Long, oldPassword: String, newPassword: String): Future[UserEntity] = {
    queryById(userId).flatMap {
      case Some(user: UserEntity) =>
        val isCorrect = user.password == oldPassword
        val newUser = user.copy(password = newPassword)
        if (isCorrect) update(newUser) else throw new IllegalArgumentException(s"Old password for user '$userId' is incorrect.")
      case None => throw new NoSuchElementException(s"User with ID '$userId' does not exist.")
    }
  }
}
