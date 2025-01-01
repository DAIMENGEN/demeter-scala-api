package com.advantest.demeter.database.table.holiday

import com.advantest.demeter.constant.country.CountryCode
import com.advantest.demeter.constant.holiday.HolidayType
import com.advantest.demeter.database.DBTableRow
import com.advantest.demeter.http.payload.HolidayPayload

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 */
final case class HolidayDBTableRow(
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

  def toEntity: HolidayPayload = HolidayPayload(
    id,
    title,
    description,
    holidayDate,
    holidayType,
    isRecurring,
    countryCode
  )
}


