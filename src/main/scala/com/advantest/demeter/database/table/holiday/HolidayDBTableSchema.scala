package com.advantest.demeter.database.table.holiday

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.constant.country.CountryCode
import com.advantest.demeter.constant.holiday.HolidayType
import com.advantest.demeter.database.{DBBooleanValue, DBDateValue, DBIntValue, DBTableSchemaAbstract, DBTextValue, DBVarcharValue}
import slick.lifted.ProvenShape

import java.time.LocalDate

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
final class HolidayDBTableSchema(tag: Tag) extends DBTableSchemaAbstract[HolidayDBTableRow](tag, "HOLIDAY_DB_TABLE") {

  def title: Rep[DBVarcharValue] = column[DBVarcharValue]("TITLE", O.SqlType("VARCHAR"), O.Length(255))

  def description: Rep[Option[DBTextValue]] = column[Option[DBTextValue]]("DESCRIPTION", O.SqlType("TEXT"))

  def holidayDate: Rep[DBDateValue] = column[DBDateValue]("HOLIDAY_DATE", O.SqlType("VARCHAR"), O.Length(10))

  def holidayType: Rep[DBIntValue] = column[DBIntValue]("HOLIDAY_TYPE")

  def isRecurring: Rep[DBBooleanValue] = column[DBBooleanValue]("IS_RECURRING")

  def countryCode: Rep[DBIntValue] = column[DBIntValue]("COUNTRY_CODE")

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
