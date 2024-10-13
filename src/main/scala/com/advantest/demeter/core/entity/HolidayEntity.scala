package com.advantest.demeter.core.entity

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.utils.database.DBEntityData
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
final case class HolidayEntity(
                                id: Long,
                                title: String,
                                description: Option[String],
                                holidayDate: LocalDate,
                                holidayType: HolidayType,
                                isRecurring: Boolean,
                                countryCode: CountryCode,
                                creatorId: Long,
                                updaterId: Long,
                                createDateTime: LocalDateTime = LocalDateTime.now(),
                                updateDateTime: LocalDateTime = LocalDateTime.now()
                              ) extends DBEntityData {

  override def toString: String = s"HolidayEntity(id=$id, title=$title, description=$description, holidayDate=$holidayDate, holidayType=$holidayType, isRecurring=$isRecurring, countryCode=$countryCode, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"
}

object HolidayEntity extends Serializable[HolidayEntity] {

  override implicit val serializeFormat: RootJsonFormat[HolidayEntity] = jsonFormat11(HolidayEntity.apply)
}