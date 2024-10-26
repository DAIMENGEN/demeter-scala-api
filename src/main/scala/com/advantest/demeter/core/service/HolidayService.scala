package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.constant.{CompanyHoliday, NationalHoliday, SpecialHoliday}
import com.advantest.demeter.core.database.holiday.HolidayTable
import com.advantest.demeter.core.entity.HolidayEntity

import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
case class HolidayService() extends HolidayTable with Service {
  private val userService = EmployeeService()

  def createHoliday(employeeId: Long, holiday: HolidayEntity): Future[HolidayEntity] = {
    if (userService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can create holidays.")
    val tableRowData = HolidayEntity.create(employeeId, holiday)
    insert(tableRowData).map(_.toEntity)
  }

  def createHolidays(employeeId: Long, holidays: Seq[HolidayEntity]): Future[Seq[HolidayEntity]] = {
    if (userService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can create holidays.")
    val tableRows = holidays.map(holiday => HolidayEntity.create(employeeId, holiday))
    batchInsert(tableRows).map(_.map(_.toEntity))
  }

  def deleteHolidays(employeeId: Long): Future[Seq[HolidayEntity]] = {
    if (userService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can delete holidays.")
    delete().map(_.map(_.toEntity))
  }

  def deleteHolidayById(employeeId: Long, holidayId: Long): Future[HolidayEntity] = {
    if (userService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can delete holidays.")
    deleteById(holidayId).map(_.toEntity)
  }

  def deleteHolidayByIds(employeeId: Long, holidayIds: Seq[Long]): Future[Seq[HolidayEntity]] = {
    if (userService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can delete holidays.")
    deleteByIds(holidayIds).map(_.map(_.toEntity))
  }

  def updateHoliday(employeeId: Long, holiday: HolidayEntity): Future[HolidayEntity] = {
    if (userService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can update holidays.")
    queryById(holiday.id).flatMap {
      case Some(oldRowData: TableRowData) =>
        val updatedTableRowData = HolidayEntity.update(employeeId, holiday, oldRowData)
        update(updatedTableRowData).map(_.toEntity)
      case None => throw new Exception("holiday not found")
    }
  }

  def updateHolidays(employeeId: Long, holidays: Seq[HolidayEntity]): Future[Seq[HolidayEntity]] = {
    if (userService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can update holidays.")
    queryByIds(holidays.map(_.id)).flatMap(oldRowDataSeq => {
      val oldRowDataMap = oldRowDataSeq.map(oldRowData => oldRowData.id -> oldRowData).toMap
      val updatedTableRowDataSeq = holidays.flatMap { holiday =>
        oldRowDataMap.get(holiday.id).map { oldRowData =>
          HolidayEntity.update(employeeId, holiday, oldRowData)
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
