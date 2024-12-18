package com.advantest.demeter.core.database.holiday

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.utils.database.DBTableSchemaAbstract
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDate

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
final class HolidayDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[HolidayDBTableRow](tag, "HOLIDAY_DB_TABLE") {

  def title: Rep[String] = column[String]("TITLE")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def holidayDate: Rep[LocalDate] = column[LocalDate]("HOLIDAY_DATE")

  def holidayType: Rep[HolidayType] = column[HolidayType]("HOLIDAY_TYPE")

  def isRecurring: Rep[Boolean] = column[Boolean]("IS_RECURRING")

  def countryCode: Rep[CountryCode] = column[CountryCode]("COUNTRY_CODE")

  override def * : ProvenShape[HolidayDBTableRow] = (
    id,
    title,
    description,
    holidayDate,
    holidayType,
    isRecurring,
    countryCode,
    creatorId,
    updaterId,
    createDateTime,
    updateDateTime()
  ) <> ((HolidayDBTableRow.apply _).tupled, HolidayDBTableRow.unapply)
}
