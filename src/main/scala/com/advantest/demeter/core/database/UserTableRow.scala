package com.advantest.demeter.core.database

import com.advantest.demeter.core.entity.UserEntity
import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 */
final case class UserTableRow(
                               id: Long,
                               account: String,
                               password: String,
                               username: String,
                               email: String,
                               phone: Option[String],
                               teamId: Option[Long],
                               departmentId: Option[Long],
                               isActive: Boolean,
                               creatorId: Long,
                               updaterId: Long,
                               createDateTime: LocalDateTime = LocalDateTime.now(),
                               updateDateTime: LocalDateTime = LocalDateTime.now()
                             ) extends DBTableRow {

  override def toString: String = s"UserTableRow(id=$id, account=$account, password=$password, username=$username, email=$email, phone=$phone, teamId=$teamId, departmentId=$departmentId, isActive=$isActive, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: UserEntity = UserEntity(
    id = id,
    account = account,
    password = password,
    username = username,
    email = email,
    phone = phone
  )
}
