package com.advantest.demeter.constant.holiday

import com.advantest.demeter.json.serialize.Serializable
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
sealed trait HolidayType {
  def toInt: Int
}

/**
 * NationalHoliday type, indicating a national holiday.
 */
case object NationalHoliday extends HolidayType {
  override def toInt: Int = 1
}

/**
 * CompanyHoliday type, indicating a company-specific holiday.
 */
case object CompanyHoliday extends HolidayType {
  override def toInt: Int = 2
}

/**
 * WeeklyHoliday type, indicating a weekly holiday.
 */
case object WeeklyHoliday extends HolidayType {
  override def toInt: Int = 3
}

/**
 * SpecialHoliday type, indicating a special holiday.
 */
case object SpecialHoliday extends HolidayType {
  override def toInt: Int = 4
}

object HolidayType extends Serializable[HolidayType] {

  def fromInt(field: Int): HolidayType = field match {
    case 1 => NationalHoliday
    case 2 => CompanyHoliday
    case 3 => WeeklyHoliday
    case 4 => SpecialHoliday
    case _ => throw new IllegalArgumentException(s"Invalid HolidayType field: $field. Valid fields are 1 (NationalHoliday), 2 (CompanyHoliday), 3 (WeeklyHoliday), 4 (SpecialHoliday).")
  }

  override implicit val serializeFormat: RootJsonFormat[HolidayType] = new RootJsonFormat[HolidayType] {
    override def write(obj: HolidayType): JsValue = JsNumber(obj.toInt)

    override def read(json: JsValue): HolidayType = json match {
      case JsNumber(value) => fromInt(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for HolidayType, but received a different type of JsValue.")
    }
  }
}