package com.advantest.demeter.core.http.payload

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.core.database.holiday.HolidayDBTableRow
import com.advantest.demeter.utils.database.DBTableRowFactory
import com.advantest.demeter.utils.http.HttpPayload
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
final case class HolidayPayload(
                                id: Long,
                                title: String,
                                description: Option[String],
                                holidayDate: LocalDate,
                                holidayType: HolidayType,
                                isRecurring: Boolean,
                                countryCode: CountryCode
                              ) extends HttpPayload {

  override def toString: String = s"HolidayPayload(id=$id, title=$title, description=$description, holidayDate=$holidayDate, holidayType=$holidayType, isRecurring=$isRecurring, countryCode=$countryCode)"
}

object HolidayPayload extends Serializable[HolidayPayload] with DBTableRowFactory {
  override protected type PayloadData = HolidayPayload
  override protected type DBTableRowData = HolidayDBTableRow

  override implicit val serializeFormat: RootJsonFormat[PayloadData] = jsonFormat7(HolidayPayload.apply)

  override def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData = HolidayDBTableRow(
    id = payloadData.id,
    title = payloadData.title,
    description = payloadData.description,
    holidayDate = payloadData.holidayDate,
    holidayType = payloadData.holidayType,
    isRecurring = payloadData.isRecurring,
    countryCode = payloadData.countryCode,
    creatorId = employeeId,
    updaterId = employeeId,
  )

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    title = payloadData.title,
    description = payloadData.description,
    holidayDate = payloadData.holidayDate,
    holidayType = payloadData.holidayType,
    isRecurring = payloadData.isRecurring,
    countryCode = payloadData.countryCode,
    updaterId = employeeId,
    updateDateTime = LocalDateTime.now()
  )
}