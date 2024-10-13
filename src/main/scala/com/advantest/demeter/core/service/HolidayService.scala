package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.DEMETER_DATABASE
import com.advantest.demeter.core.constant.{CompanyHoliday, NationalHoliday, SpecialHoliday}
import com.advantest.demeter.core.database.HolidayTable
import com.advantest.demeter.core.entity.HolidayEntity

import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
case class HolidayService() extends HolidayTable with Service {

  def createHoliday(holiday: HolidayEntity): Future[HolidayEntity] = insert(holiday)

  def createHolidays(holidays: Seq[HolidayEntity]): Future[Seq[HolidayEntity]] = batchInsert(holidays)

  def deleteHolidays(): Future[Seq[HolidayEntity]] = delete()

  def deleteHolidayById(holidayId: Long): Future[HolidayEntity] = deleteById(holidayId)

  def deleteHolidayByIds(holidayIds: Seq[Long]): Future[Seq[HolidayEntity]] = deleteByIds(holidayIds)

  def updateHoliday(holiday: HolidayEntity): Future[HolidayEntity] = update(holiday)

  def updateHolidays(holidays: Seq[HolidayEntity]): Future[Seq[HolidayEntity]] = update(holidays)

  def getHolidays: Future[Seq[HolidayEntity]] = query()

  def getNationalHolidays: Future[Seq[HolidayEntity]] = getHolidaysByType(NationalHoliday)

  def getCompanyHolidays: Future[Seq[HolidayEntity]] = getHolidaysByType(CompanyHoliday)

  def getSpecialWorkdays: Future[Seq[HolidayEntity]] = getHolidaysByType(SpecialHoliday)
}
