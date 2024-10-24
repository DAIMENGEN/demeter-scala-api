package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.constant.{CompanyHoliday, NationalHoliday, SpecialHoliday}
import com.advantest.demeter.core.database.HolidayTable
import com.advantest.demeter.core.entity.{HolidayEntity, UserEntity}

import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
case class HolidayService() extends HolidayTable with Service {

  def createHoliday(user: UserEntity, holiday: HolidayEntity): Future[HolidayEntity] = {
    val tableRowData = HolidayEntity.create(user.id, holiday)
    insert(tableRowData).map(_.toEntity)
  }

  def createHolidays(user: UserEntity, holidays: Seq[HolidayEntity]): Future[Seq[HolidayEntity]] = {
    val tableRows = holidays.map(holiday => HolidayEntity.create(user.id, holiday))
    batchInsert(tableRows).map(_.map(_.toEntity))
  }

  def deleteHolidays(): Future[Seq[HolidayEntity]] = delete().map(_.map(_.toEntity))

  def deleteHolidayById(holidayId: Long): Future[HolidayEntity] = deleteById(holidayId).map(_.toEntity)

  def deleteHolidayByIds(holidayIds: Seq[Long]): Future[Seq[HolidayEntity]] = deleteByIds(holidayIds).map(_.map(_.toEntity))

  def updateHoliday(user: UserEntity, holiday: HolidayEntity): Future[HolidayEntity] = {
    queryById(holiday.id).flatMap {
      case Some(oldRowData: TableRowData) =>
        val updatedTableRowData = HolidayEntity.update(user.id, holiday, oldRowData)
        update(updatedTableRowData).map(_.toEntity)
      case None => throw new Exception("holiday not found")
    }
  }

  def updateHolidays(user: UserEntity, holidays: Seq[HolidayEntity]): Future[Seq[HolidayEntity]] = {
    queryByIds(holidays.map(_.id)).flatMap(oldRowDataSeq => {
      val oldRowDataMap = oldRowDataSeq.map(oldRowData => oldRowData.id -> oldRowData).toMap
      val updatedTableRowDataSeq = holidays.flatMap { holiday =>
        oldRowDataMap.get(holiday.id).map { oldRowData =>
          HolidayEntity.update(user.id, holiday, oldRowData)
        }
      }
      update(updatedTableRowDataSeq).map(_.map(_.toEntity))
    })
  }

  def getHolidays: Future[Seq[HolidayEntity]] = query().map(_.map(_.toEntity))

  def getNationalHolidays: Future[Seq[HolidayEntity]] = getHolidaysByType(NationalHoliday).map(_.map(_.toEntity))

  def getCompanyHolidays: Future[Seq[HolidayEntity]] = getHolidaysByType(CompanyHoliday).map(_.map(_.toEntity))

  def getSpecialWorkdays: Future[Seq[HolidayEntity]] = getHolidaysByType(SpecialHoliday).map(_.map(_.toEntity))
}
