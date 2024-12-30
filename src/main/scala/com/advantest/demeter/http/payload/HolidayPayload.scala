package com.advantest.demeter.http.payload

import com.advantest.demeter.constant.{CountryCode, HolidayType}
import com.advantest.demeter.database.DBTableRowFactory
import com.advantest.demeter.database.table.holiday.HolidayDBTableRow
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
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