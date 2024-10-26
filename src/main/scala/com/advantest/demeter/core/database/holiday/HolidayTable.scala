package com.advantest.demeter.core.database.holiday

import com.advantest.demeter.core.constant.{CountryCode, HolidayType}
import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import java.time.LocalDate
import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
final case class HolidayTable(implicit val db: Database) extends DBTable {
  override protected type TableRowData = HolidayTableRow
  override protected val table: TableQuery[HolidayTableSchema] = TableQuery[HolidayTableSchema]
  createTableIfNotExists()

  def queryByTitle(title: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.title === title).result
    db.run(select)
  }

  def queryByTitleLike(titlePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.title like s"%$titlePattern%").result
    db.run(select)
  }

  def queryByDescriptionLike(descriptionPattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.description like s"%$descriptionPattern%").result
    db.run(select)
  }

  def queryByHolidayDate(holidayDate: LocalDate): Future[Seq[TableRowData]] = {
    val select = table.filter(_.holidayDate === holidayDate).result
    db.run(select)
  }

  def queryByHolidayDates(holidayDates: Seq[LocalDate]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.holidayDate.inSet(holidayDates)).result
    db.run(select)
  }

  def queryByIsRecurring(isRecurring: Boolean): Future[Seq[TableRowData]] = {
    val select = table.filter(_.isRecurring === isRecurring).result
    db.run(select)
  }

  def queryByCountryCode(countryCode: CountryCode): Future[Seq[TableRowData]] = {
    val select = table.filter(_.countryCode === countryCode).result
    db.run(select)
  }

  def queryByCountryCodes(countryCodes: Seq[CountryCode]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.countryCode.inSet(countryCodes)).result
    db.run(select)
  }

  def queryByHolidayType(holidayType: HolidayType): Future[Seq[TableRowData]] = {
    val select = table.filter(_.holidayType === holidayType).result
    db.run(select)
  }
}
