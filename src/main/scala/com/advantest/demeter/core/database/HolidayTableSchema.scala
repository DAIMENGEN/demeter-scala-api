package com.advantest.demeter.core.database

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.utils.database.DBTableSchema
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
class HolidayTableSchema(tag: Tag) extends Table[HolidayTableRow](tag, "HOLIDAY_TABLE") with DBTableSchema {

  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  def title: Rep[String] = column[String]("TITLE")

  def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")

  def holidayDate: Rep[LocalDate] = column[LocalDate]("HOLIDAY_DATE")

  def holidayType: Rep[HolidayType] = column[HolidayType]("HOLIDAY_TYPE")

  def isRecurring: Rep[Boolean] = column[Boolean]("IS_RECURRING")

  def countryCode: Rep[CountryCode] = column[CountryCode]("COUNTRY_CODE")

  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))

  override def * : ProvenShape[HolidayTableRow] = (
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
  ) <> ((HolidayTableRow.apply _).tupled, HolidayTableRow.unapply)
}
