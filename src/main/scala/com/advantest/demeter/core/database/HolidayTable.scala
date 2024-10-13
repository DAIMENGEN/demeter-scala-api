package com.advantest.demeter.core.database

import com.advantest.demeter.core.constant.HolidayType
import com.advantest.demeter.core.entity.HolidayEntity
import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
class HolidayTable(implicit val db: Database) extends DBTable {
  override protected type EntityData = HolidayEntity
  override protected val table: TableQuery[HolidayTableDescriptor] = TableQuery[HolidayTableDescriptor]
  createTableIfNotExists()

  def getHolidaysByType(holidayType: HolidayType): Future[Seq[HolidayEntity]] = {
    val select = table.filter(_.holidayType === holidayType).result
    db.run(select)
  }
}
