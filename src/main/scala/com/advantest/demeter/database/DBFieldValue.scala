package com.advantest.demeter.database

import com.advantest.demeter.json.JsonObject
import com.advantest.demeter.json.serialize.Serializable
import com.advantest.demeter.utils.{DateUtils, JsonUtils}
import slick.jdbc.MySQLProfile.api._
import spray.json.DefaultJsonProtocol._
import spray.json._

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
sealed trait DBFieldValue

/**
 * DBIntValue, representing an int value in the database.
 *
 * @param value The actual int value.
 */
case class DBIntValue(value: Int) extends DBFieldValue

object DBIntValue extends DBTableColumn {
  override type ModelType = DBIntValue
  override type FieldType = Int

  override def fromModel(model: DBIntValue): Int = model.value

  override def fromField(field: Int): DBIntValue = DBIntValue(field)

  override implicit def columnMapper: BaseColumnType[DBIntValue] = MappedColumnType.base[DBIntValue, Int](fromModel, fromField)
}

/**
 * DBLongValue, representing a long value in the database.
 *
 * @param value The actual long value.
 */
case class DBLongValue(value: Long) extends DBFieldValue

object DBLongValue extends DBTableColumn {
  override type ModelType = DBLongValue
  override type FieldType = Long

  override def fromModel(model: DBLongValue): Long = model.value

  override def fromField(field: Long): DBLongValue = DBLongValue(field)

  override implicit def columnMapper: BaseColumnType[DBLongValue] = MappedColumnType.base[DBLongValue, Long](fromModel, fromField)
}

/**
 * DBFloatValue, representing a float value in the database.
 *
 * @param value The actual float value.
 */
case class DBFloatValue(value: Float) extends DBFieldValue

object DBFloatValue extends DBTableColumn {
  override type ModelType = DBFloatValue
  override type FieldType = Float

  override def fromModel(model: DBFloatValue): Float = model.value

  override def fromField(field: Float): DBFloatValue = DBFloatValue(field)

  override implicit def columnMapper: BaseColumnType[DBFloatValue] = MappedColumnType.base[DBFloatValue, Float](fromModel, fromField)
}

/**
 * DBDoubleValue, representing a double value in the database.
 *
 * @param value The actual double value.
 */
case class DBDoubleValue(value: Double) extends DBFieldValue

object DBDoubleValue extends DBTableColumn {
  override type ModelType = DBDoubleValue
  override type FieldType = Double

  override def fromModel(model: DBDoubleValue): Double = model.value

  override def fromField(field: Double): DBDoubleValue = DBDoubleValue(field)

  override implicit def columnMapper: BaseColumnType[DBDoubleValue] = MappedColumnType.base[DBDoubleValue, Double](fromModel, fromField)
}

/**
 * DBVarcharValue, representing a string value in the database.
 *
 * @param value The actual string value and string length <= 255.
 */
case class DBVarcharValue(value: String) extends DBFieldValue

object DBVarcharValue extends DBTableColumn {
  override type ModelType = DBVarcharValue
  override type FieldType = String

  override def fromModel(model: DBVarcharValue): String = model.value

  override def fromField(field: String): DBVarcharValue = DBVarcharValue(field)

  override implicit def columnMapper: BaseColumnType[DBVarcharValue] = MappedColumnType.base[DBVarcharValue, String](fromModel, fromField)
}

/**
 * DBTextValue, representing a text value in the database.
 * @param value The actual string value and string length > 255 and <= 65,535. (64KB)
 */
case class DBTextValue(value: String) extends DBFieldValue
object DBTextValue extends DBTableColumn {
  override type ModelType = DBTextValue
  override type FieldType = String

  override def fromModel(model: DBTextValue): String = model.value

  override def fromField(field: String): DBTextValue = DBTextValue(field)

  override implicit def columnMapper: BaseColumnType[DBTextValue] = MappedColumnType.base[DBTextValue, String](fromModel, fromField)
}

/**
 * DBMediumtextValue, representing a mediumtext value in the database.
 * @param value The actual string value and string length > 65535 and <= 16,777,215. (16MB)
 */
case class DBMediumtextValue(value: String) extends DBFieldValue
object DBMediumtextValue extends DBTableColumn {
  override type ModelType = DBMediumtextValue
  override type FieldType = String

  override def fromModel(model: DBMediumtextValue): String = model.value

  override def fromField(field: String): DBMediumtextValue = DBMediumtextValue(field)

  override implicit def columnMapper: BaseColumnType[DBMediumtextValue] = MappedColumnType.base[DBMediumtextValue, String](fromModel, fromField)
}

/**
 * DBLongtextValue, representing a longtext value in the database.
 * @param value The actual string value and string length > 16,777,215 and <= 4,294,967,295 . (4GB)
 */
case class DBLongtextValue(value: String) extends DBFieldValue
object DBLongtextValue extends DBTableColumn {
  override type ModelType = DBLongtextValue
  override type FieldType = String

  override def fromModel(model: DBLongtextValue): String = model.value

  override def fromField(field: String): DBLongtextValue = DBLongtextValue(field)

  override implicit def columnMapper: BaseColumnType[DBLongtextValue] = MappedColumnType.base[DBLongtextValue, String](fromModel, fromField)
}

/**
 * DBBooleanValue, representing a boolean value in the database.
 *
 * @param value The actual boolean value.
 */
case class DBBooleanValue(value: Boolean) extends DBFieldValue

object DBBooleanValue extends DBTableColumn {
  override type ModelType = DBBooleanValue
  override type FieldType = Boolean

  override def fromModel(model: DBBooleanValue): Boolean = model.value

  override def fromField(field: Boolean): DBBooleanValue = DBBooleanValue(field)

  override implicit def columnMapper: BaseColumnType[DBBooleanValue] = MappedColumnType.base[DBBooleanValue, Boolean](fromModel, fromField)
}


/**
 * DBJsonValue, representing a json value in the database.
 *
 * @param value The actual string value.
 */
case class DBJsonValue(value: JsonObject) extends DBFieldValue

object DBJsonValue extends DBTableColumn {
  override type ModelType = DBJsonValue
  override type FieldType = String

  override def fromModel(model: DBJsonValue): String = model.value.toString

  override def fromField(field: String): DBJsonValue = DBJsonValue(JsonObject(field))

  override implicit def columnMapper: BaseColumnType[DBJsonValue] = MappedColumnType.base[DBJsonValue, String](fromModel, fromField)
}

/**
 * DBDateValue, representing a string value in the format YYYY-MM-DD in the database.
 *
 * @param value The actual string value.
 */
case class DBDateValue(value: LocalDate) extends DBFieldValue

object DBDateValue extends DBTableColumn {
  override type ModelType = DBDateValue
  override type FieldType = String

  override def fromModel(model: DBDateValue): String = DateUtils.formatLocalDate(model.value)

  override def fromField(field: String): DBDateValue = DBDateValue(DateUtils.parseLocalDate(field))

  override implicit def columnMapper: BaseColumnType[DBDateValue] = MappedColumnType.base[DBDateValue, String](fromModel, fromField)
}

/**
 * DBDateTimeValue, representing a string value in the format YYYY-MM-DD HH:mm:ss in the database.
 *
 * @param value The actual string value.
 */
case class DBDateTimeValue(value: LocalDateTime) extends DBFieldValue

object DBDateTimeValue extends DBTableColumn {
  override type ModelType = DBDateTimeValue
  override type FieldType = String

  override def fromModel(model: DBDateTimeValue): String = DateUtils.formatLocalDateTime(model.value)

  override def fromField(field: String): DBDateTimeValue = DBDateTimeValue(DateUtils.parseLocalDateTime(field))

  override implicit def columnMapper: BaseColumnType[DBDateTimeValue] = MappedColumnType.base[DBDateTimeValue, String](fromModel, fromField)
}

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
      case DBVarcharValue(value) => value.toJson
      case DBTextValue(value) => value.toJson
      case DBMediumtextValue(value) => value.toJson
      case DBLongtextValue(value) => value.toJson
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
        } else if (value.length <= 255) {
          DBVarcharValue(value)
        } else if (value.length <= 65_535) {
          DBTextValue(value)
        } else if (value.length <= 16_777_215) {
          DBMediumtextValue(value)
        } else {
          DBLongtextValue(value)
        }
      case JsBoolean(value) => DBBooleanValue(value)
      case _ => throw new IllegalArgumentException(s"Expected JsString, JsNumber, JsNumber as DBFieldValue, but received a $json")
    }
  }
}