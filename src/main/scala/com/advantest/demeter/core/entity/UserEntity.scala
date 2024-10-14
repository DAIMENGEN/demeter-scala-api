package com.advantest.demeter.core.entity

import com.advantest.demeter.utils.database.DBEntityData
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
                             teamId: Option[Long],
                             departmentId: Option[Long],
                             isActive: Boolean,
                             creatorId: Long,
                             updaterId: Long,
                             createDateTime: LocalDateTime = LocalDateTime.now(),
                             updateDateTime: LocalDateTime = LocalDateTime.now()
                           ) extends DBEntityData {

  override def toString: String = s"UserEntity(id=$id, account=$account, password=$password, username=$username, email=$email, phone=$phone, teamId=$teamId, departmentId=$departmentId, isActive=$isActive, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}

object UserEntity extends Serializable[UserEntity] {
  override implicit val serializeFormat: RootJsonFormat[UserEntity] = jsonFormat13(UserEntity.apply)
}
