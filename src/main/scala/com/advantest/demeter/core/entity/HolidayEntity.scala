package com.advantest.demeter.core.entity

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.core.database.holiday.HolidayDBTableRow
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
  override protected type TableRowData = HolidayDBTableRow

  override implicit val serializeFormat: RootJsonFormat[EntityData] = jsonFormat7(HolidayEntity.apply)

  override def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData = HolidayDBTableRow(
    id = entityData.id,
    title = entityData.title,
    description = entityData.description,
    holidayDate = entityData.holidayDate,
    holidayType = entityData.holidayType,
    isRecurring = entityData.isRecurring,
    countryCode = entityData.countryCode,
    creatorId = employeeId,
    updaterId = employeeId,
  )

  override def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData = oldRowData.copy(
    title = entityData.title,
    description = entityData.description,
    holidayDate = entityData.holidayDate,
    holidayType = entityData.holidayType,
    isRecurring = entityData.isRecurring,
    countryCode = entityData.countryCode,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}