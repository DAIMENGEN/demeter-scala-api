package com.advantest.demeter.utils.database

import com.advantest.demeter.utils.date.DateUtils
import com.advantest.demeter.utils.json.{JsonObject, JsonUtils}
import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json._

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
sealed trait DBFieldValue

/**
 * DBIntValue, representing an int field in the database.
 *
 * @param value The actual int value.
 */
case class DBIntValue(value: Int) extends DBFieldValue

/**
 * DBLongValue, representing a long field in the database.
 *
 * @param value The actual long value.
 */
case class DBLongValue(value: Long) extends DBFieldValue

/**
 * DBFloatValue, representing a float field in the database.
 *
 * @param value The actual float value.
 */
case class DBFloatValue(value: Float) extends DBFieldValue

/**
 * DBDoubleValue, representing a double field in the database.
 *
 * @param value The actual double value.
 */
case class DBDoubleValue(value: Double) extends DBFieldValue

/**
 * DBStringValue, representing a string field in the database.
 *
 * @param value The actual string value.
 */
case class DBStringValue(value: String) extends DBFieldValue

/**
 * DBBooleanValue, representing a boolean field in the database.
 *
 * @param value The actual boolean value.
 */
case class DBBooleanValue(value: Boolean) extends DBFieldValue

/**
 * DBJsonValue, representing a json field in the database.
 *
 * @param value The actual json value.
 */
case class DBJsonValue(value: JsonObject) extends DBFieldValue

/**
 * DBDateValue, representing a date field in the database.
 *
 * @param value The actual date value.
 */
case class DBDateValue(value: LocalDate) extends DBFieldValue

/**
 * DateTimeValue, representing a datetime field in the database.
 *
 * @param value The actual datetime value.
 */
case class DBDateTimeValue(value: LocalDateTime) extends DBFieldValue


object DBFieldValue extends Serializable[DBFieldValue] {

  /**
   * Implicit value that defines the serialization format for type T.
   * This format is a root JSON format, meaning it can serialize and deserialize objects of type T.
   */
  override implicit val serializeFormat: RootJsonFormat[DBFieldValue] = new RootJsonFormat[DBFieldValue] {
    override def write(obj: DBFieldValue): JsValue = obj match {
      case DBIntValue(value) => value.toJson
      case DBLongValue(value) => value.toJson
      case DBFloatValue(value) => value.toJson
      case DBDoubleValue(value) => value.toJson
      case DBStringValue(value) => value.toJson
      case DBBooleanValue(value) => value.toJson
      case DBJsonValue(value) => value.toJson
      case DBDateValue(value) => value.toJson
      case DBDateTimeValue(value) => value.toJson
    }

    override def read(json: JsValue): DBFieldValue = json match {
      case JsNumber(value) =>
        if (value.isValidInt) {
          DBIntValue(value.toInt)
        } else if (value.isValidLong) {
          DBLongValue(value.toLong)
        } else if (value.isDecimalFloat) {
          DBFloatValue(value.toFloat)
        } else if (value.isDecimalFloat) {
          DBDoubleValue(value.toDouble)
        } else {
          throw new IllegalArgumentException(s"Invalid number value: $value")
        }
      case JsString(value) =>
        if (DateUtils.isValidDate(value)) {
          DBDateValue(DateUtils.parseLocalDate(value))
        } else if (DateUtils.isValidDateTime(value)) {
          DBDateTimeValue(DateUtils.parseLocalDateTime(value))
        } else if (JsonUtils.isValidJson(value)) {
          DBJsonValue(JsonObject(value))
        } else {
          DBStringValue(value)
        }
      case JsBoolean(value) => DBBooleanValue(value)
      case _ => throw new IllegalArgumentException(s"Expected JsString, JsNumber, JsNumber as DBFieldValue, but received a $json")
    }
  }
}