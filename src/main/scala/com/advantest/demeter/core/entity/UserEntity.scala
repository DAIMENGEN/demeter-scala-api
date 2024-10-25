package com.advantest.demeter.core.entity

import com.advantest.demeter.core.database.UserTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDateTime

/**
 * Create on 2024/10/14
 * Author: mengen.dai@outlook.com
 */
final case class UserEntity(
                             id: Long,
                             account: String,
                             password: String,
                             username: String,
                             email: String,
                             phone: Option[String],
                             isActive: Boolean
                           ) {
  override def toString: String = s"UserEntity(id=$id, account=$account, password=$password, username=$username, email=$email, phone=$phone)"
}

object UserEntity extends Serializable[UserEntity] with DBTableRowFactory {
  override protected type EntityData = UserEntity
  override protected type TableRowData = UserTableRow

  override implicit val serializeFormat: RootJsonFormat[UserEntity] = jsonFormat7(UserEntity.apply)

  override def create(userId: Long, entityData: UserEntity): UserTableRow = UserTableRow(
    id = entityData.id,
    account = entityData.account,
    password = entityData.password,
    username = entityData.username,
    email = entityData.email,
    phone = entityData.phone,
    teamId = None, // TODO: teamId and departmentId are not supported yet
    departmentId = None,
    isActive = entityData.isActive,
    creatorId = userId,
    updaterId = userId,
  )

  override def update(userId: Long, entityData: UserEntity, oldRowData: UserTableRow): UserTableRow = oldRowData.copy(
    account = entityData.account,
    password = entityData.password,
    username = entityData.username,
    email = entityData.email,
    phone = entityData.phone,
    teamId = None, // TODO: teamId and departmentId are not supported yet
    departmentId = None,
    isActive = entityData.isActive,
    updaterId = userId,
    updateDateTime = LocalDateTime.now()
  )

  val SystemAdminId: Long = 1
}
