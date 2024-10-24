package com.advantest.demeter.core.database

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.core.entity.HolidayEntity
import com.advantest.demeter.utils.database.DBTableRow

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 */
final case class HolidayTableRow(
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
                                ) extends DBTableRow {

  override def toString: String = s"HolidayTableRow(id=$id, title=$title, description=$description, holidayDate=$holidayDate, holidayType=$holidayType, isRecurring=$isRecurring, countryCode=$countryCode, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: HolidayEntity = HolidayEntity(
    id,
    title,
    description,
    holidayDate,
    holidayType,
    isRecurring,
    countryCode
  )
}


