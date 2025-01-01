package com.advantest.demeter.constant.country

import com.advantest.demeter.database.DBTableColumn
import com.advantest.demeter.json.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
sealed trait CountryCode {
  def toInt: Int
}

/**
 * ChinaCode, representing China.
 */
case object ChinaCode extends CountryCode {
  override def toInt: Int = 1
}

/**
 * JapanCode, representing Japan.
 */
case object JapanCode extends CountryCode {
  override def toInt: Int = 2
}

/**
 * AmericaCode, representing America.
 */
case object AmericaCode extends CountryCode {
  override def toInt: Int = 3
}

/**
 * GermanyCode, representing Germany.
 */
case object GermanyCode extends CountryCode {
  override def toInt: Int = 4
}

object CountryCode extends DBTableColumn with Serializable[CountryCode] {
  override type ModelType = CountryCode
  override type FieldType = Int

  override def fromModel(model: CountryCode): Int = model.toInt

  override def fromField(field: Int): CountryCode = field match {
    case 1 => ChinaCode
    case 2 => JapanCode
    case 3 => AmericaCode
    case 4 => GermanyCode
    case _ => throw new IllegalArgumentException(s"Invalid country code: $field. Valid fields are 1 (ChinaCode), 2 (JapanCode), 3 (AmericaCode), 4 (GermanyCode).")
  }

  override implicit def columnMapper: BaseColumnType[CountryCode] = MappedColumnType.base[CountryCode, Int](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[CountryCode] = new RootJsonFormat[CountryCode] {
    override def write(obj: CountryCode): JsValue = JsNumber(fromModel(obj))

    override def read(json: JsValue): CountryCode = json match {
      case JsNumber(value) => fromField(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for CountryCode, but received a different type of JsValue.")
    }
  }
}