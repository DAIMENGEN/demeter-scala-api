package com.advantest.demeter.constant

import com.advantest.demeter.database.DBTableColumn
import com.advantest.demeter.json.serialize.Serializable
import spray.json.{JsNumber, JsValue, RootJsonFormat}
import slick.jdbc.MySQLProfile.api._

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
sealed trait HolidayType

/**
 * NationalHoliday type, indicating a national holiday.
 */
case object NationalHoliday extends HolidayType

/**
 * CompanyHoliday type, indicating a company-specific holiday.
 */
case object CompanyHoliday extends HolidayType

/**
 * WeeklyHoliday type, indicating a weekly holiday.
 */
case object WeeklyHoliday extends HolidayType

/**
 * SpecialHoliday type, indicating a special holiday.
 */
case object SpecialHoliday extends HolidayType

object HolidayType extends DBTableColumn with Serializable[HolidayType] {
  override type ModelType = HolidayType
  override type FieldType = Int

  override def fromModel(model: HolidayType): Int = model match {
    case NationalHoliday => 1
    case CompanyHoliday => 2
    case WeeklyHoliday => 3
    case SpecialHoliday => 4
  }

  override def fromField(field: Int): HolidayType = field match {
    case 1 => NationalHoliday
    case 2 => CompanyHoliday
    case 3 => WeeklyHoliday
    case 4 => SpecialHoliday
    case _ => throw new IllegalArgumentException(s"Invalid HolidayType field: $field. Valid fields are 1 (NationalHoliday), 2 (CompanyHoliday), 3 (WeeklyHoliday), 4 (SpecialHoliday).")
  }

  override implicit def columnMapper: BaseColumnType[HolidayType] = MappedColumnType.base[HolidayType, Int](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[HolidayType] = new RootJsonFormat[HolidayType] {
    override def write(obj: HolidayType): JsValue = JsNumber(fromModel(obj))

    override def read(json: JsValue): HolidayType = json match {
      case JsNumber(value) => fromField(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for HolidayType, but received a different type of JsValue.")
    }
  }
}