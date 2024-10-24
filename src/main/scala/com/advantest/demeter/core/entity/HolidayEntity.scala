package com.advantest.demeter.core.entity

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.core.database.HolidayTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
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
                                countryCode: CountryCode
                              ) {

  override def toString: String = s"HolidayEntity(id=$id, title=$title, description=$description, holidayDate=$holidayDate, holidayType=$holidayType, isRecurring=$isRecurring, countryCode=$countryCode)"
}

object HolidayEntity extends Serializable[HolidayEntity] with DBTableRowFactory {
  override protected type EntityData = HolidayEntity
  override protected type TableRowData = HolidayTableRow

  override implicit val serializeFormat: RootJsonFormat[HolidayEntity] = jsonFormat7(HolidayEntity.apply)

  override def create(userId: Long, entityData: HolidayEntity): HolidayTableRow = HolidayTableRow(
    id = entityData.id,
    title = entityData.title,
    description = entityData.description,
    holidayDate = entityData.holidayDate,
    holidayType = entityData.holidayType,
    isRecurring = entityData.isRecurring,
    countryCode = entityData.countryCode,
    creatorId = userId,
    updaterId = userId,
  )

  override def update(userId: Long, entityData: HolidayEntity, oldRowData: HolidayTableRow): HolidayTableRow = oldRowData.copy(
    title = entityData.title,
    description = entityData.description,
    holidayDate = entityData.holidayDate,
    holidayType = entityData.holidayType,
    isRecurring = entityData.isRecurring,
    countryCode = entityData.countryCode,
    updaterId = userId,
    updateDateTime = LocalDateTime.now()
  )
}