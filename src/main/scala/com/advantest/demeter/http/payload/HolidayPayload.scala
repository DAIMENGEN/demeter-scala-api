package com.advantest.demeter.http.payload

import com.advantest.demeter.constant.country.CountryCode
import com.advantest.demeter.constant.holiday.HolidayType
import com.advantest.demeter.database.table.holiday.HolidayDBTableRow
import com.advantest.demeter.database._
import com.advantest.demeter.http.HttpPayload
import com.advantest.demeter.json.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import java.time.LocalDate

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
    id = DBLongValue(payloadData.id),
    title = DBVarcharValue(payloadData.title),
    description = payloadData.description.map(d => DBTextValue(d)),
    holidayDate = DBDateValue(payloadData.holidayDate),
    holidayType = DBIntValue(payloadData.holidayType.toInt),
    isRecurring = DBBooleanValue(payloadData.isRecurring),
    countryCode = DBIntValue(payloadData.countryCode.toInt),
    creatorId = DBLongValue(employeeId),
    updaterId = DBLongValue(employeeId),
  )

  override def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData = oldRowData.copy(
    title = DBVarcharValue(payloadData.title),
    description = payloadData.description.map(d => DBTextValue(d)),
    holidayDate = DBDateValue(payloadData.holidayDate),
    holidayType = DBIntValue(payloadData.holidayType.toInt),
    isRecurring = DBBooleanValue(payloadData.isRecurring),
    countryCode = DBIntValue(payloadData.countryCode.toInt),
    updaterId = DBLongValue(employeeId),
    updateDateTime = DBDateTimeValue.now()
  )
}