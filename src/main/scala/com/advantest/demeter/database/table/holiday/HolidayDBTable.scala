package com.advantest.demeter.database.table.holiday

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.constant.country.CountryCode
import com.advantest.demeter.constant.holiday.HolidayType
import com.advantest.demeter.database._

import java.time.LocalDate
import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
final case class HolidayDBTable()(implicit val db: Database) extends DBTable {
  override protected type TableRowData = HolidayDBTableRow
  override protected val table: TableQuery[HolidayDBTableSchema] = TableQuery[HolidayDBTableSchema]
  createTableIfNotExists()

  def queryByTitle(title: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.title === DBVarcharValue(title)).result
    db.run(select)
  }

  def queryByTitleLike(titlePattern: String): Future[Seq[TableRowData]] = {
    val select = table.filter(_.title.asColumnOf[String] like s"%$titlePattern%").result
    db.run(select)
  }


  def queryByHolidayDate(holidayDate: LocalDate): Future[Seq[TableRowData]] = {
    val select = table.filter(_.holidayDate === DBDateValue(holidayDate)).result
    db.run(select)
  }

  def queryByHolidayDates(holidayDates: Seq[LocalDate]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.holidayDate.inSet(holidayDates.map(date => DBDateValue(date)))).result
    db.run(select)
  }

  def queryByIsRecurring(isRecurring: Boolean): Future[Seq[TableRowData]] = {
    val select = table.filter(_.isRecurring === DBBooleanValue(isRecurring)).result
    db.run(select)
  }

  def queryByCountryCode(countryCode: CountryCode): Future[Seq[TableRowData]] = {
    val select = table.filter(_.countryCode === DBIntValue(countryCode.toInt)).result
    db.run(select)
  }

  def queryByCountryCodes(countryCodes: Seq[CountryCode]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.countryCode.inSet(countryCodes.map(code => DBIntValue(code.toInt)))).result
    db.run(select)
  }

  def queryByHolidayType(holidayType: HolidayType): Future[Seq[TableRowData]] = {
    val select = table.filter(_.holidayType === DBIntValue(holidayType.toInt)).result
    db.run(select)
  }
}
