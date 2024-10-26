package com.advantest.demeter.core.database.holiday

import com.advantest.demeter.core.constant.HolidayType
import com.advantest.demeter.utils.database.DBTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
final case class HolidayTable(implicit val db: Database) extends DBTable {
  override protected type TableRowData = HolidayTableRow
  override protected val table: TableQuery[HolidayTableSchema] = TableQuery[HolidayTableSchema]
  createTableIfNotExists()

  def getHolidaysByType(holidayType: HolidayType): Future[Seq[TableRowData]] = {
    val select = table.filter(_.holidayType === holidayType).result
    db.run(select)
  }
}
