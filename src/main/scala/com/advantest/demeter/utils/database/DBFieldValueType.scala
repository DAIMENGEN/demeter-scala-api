package com.advantest.demeter.utils.database

import com.advantest.demeter.utils.date.DateUtils
import com.advantest.demeter.utils.json.{JsonObject, JsonUtils}
import com.advantest.demeter.utils.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.DefaultJsonProtocol._
import spray.json._

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
sealed trait DBFieldValueType

/**
 * DBIntTypeValue, representing an int field in the database.
 *
 * @param value The actual int value.
 */
case class DBIntTypeValue(value: Int) extends DBFieldValueType

object DBIntTypeValue extends DBTableColumn {
  override type ModelType = DBIntTypeValue
  override type FieldType = Int

  override def fromModel(model: DBIntTypeValue): Int = model.value

  override def fromField(field: Int): DBIntTypeValue = DBIntTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBIntTypeValue] = MappedColumnType.base[DBIntTypeValue, Int](fromModel, fromField)
}

/**
 * DBLongTypeValue, representing a long field in the database.
 *
 * @param value The actual long value.
 */
case class DBLongTypeValue(value: Long) extends DBFieldValueType

object DBLongTypeValue extends DBTableColumn {
  override type ModelType = DBLongTypeValue
  override type FieldType = Long

  override def fromModel(model: DBLongTypeValue): Long = model.value

  override def fromField(field: Long): DBLongTypeValue = DBLongTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBLongTypeValue] = MappedColumnType.base[DBLongTypeValue, Long](fromModel, fromField)
}

/**
 * DBFloatTypeValue, representing a float field in the database.
 *
 * @param value The actual float value.
 */
case class DBFloatTypeValue(value: Float) extends DBFieldValueType

object DBFloatTypeValue extends DBTableColumn {
  override type ModelType = DBFloatTypeValue
  override type FieldType = Float

  override def fromModel(model: DBFloatTypeValue): Float = model.value

  override def fromField(field: Float): DBFloatTypeValue = DBFloatTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBFloatTypeValue] = MappedColumnType.base[DBFloatTypeValue, Float](fromModel, fromField)
}

/**
 * DBDoubleTypeValue, representing a double field in the database.
 *
 * @param value The actual double value.
 */
case class DBDoubleTypeValue(value: Double) extends DBFieldValueType

object DBDoubleTypeValue extends DBTableColumn {
  override type ModelType = DBDoubleTypeValue
  override type FieldType = Double

  override def fromModel(model: DBDoubleTypeValue): Double = model.value

  override def fromField(field: Double): DBDoubleTypeValue = DBDoubleTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBDoubleTypeValue] = MappedColumnType.base[DBDoubleTypeValue, Double](fromModel, fromField)
}

/**
 * DBStringTypeValue, representing a string field in the database.
 *
 * @param value The actual string value and string length <= 255.
 */
case class DBStringTypeValue(value: String) extends DBFieldValueType

object DBStringTypeValue extends DBTableColumn {
  override type ModelType = DBStringTypeValue
  override type FieldType = String

  override def fromModel(model: DBStringTypeValue): String = model.value

  override def fromField(field: String): DBStringTypeValue = DBStringTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBStringTypeValue] = MappedColumnType.base[DBStringTypeValue, String](fromModel, fromField)
}

/**
 * DBTextTypeValue, representing a text field in the database.
 * @param value The actual string value and string length > 255 and <= 65,535. (64KB)
 */
case class DBTextTypeValue(value: String) extends DBFieldValueType
object DBTextTypeValue extends DBTableColumn {
  override type ModelType = DBTextTypeValue
  override type FieldType = String

  override def fromModel(model: DBTextTypeValue): String = model.value

  override def fromField(field: String): DBTextTypeValue = DBTextTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBTextTypeValue] = MappedColumnType.base[DBTextTypeValue, String](fromModel, fromField)
}

/**
 * DBMediumtextTypeValue, representing a medium text field in the database.
 * @param value The actual string value and string length > 65535 and <= 16,777,215. (16MB)
 */
case class DBMediumtextTypeValue(value: String) extends DBFieldValueType
object DBMediumtextTypeValue extends DBTableColumn {
  override type ModelType = DBMediumtextTypeValue
  override type FieldType = String

  override def fromModel(model: DBMediumtextTypeValue): String = model.value

  override def fromField(field: String): DBMediumtextTypeValue = DBMediumtextTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBMediumtextTypeValue] = MappedColumnType.base[DBMediumtextTypeValue, String](fromModel, fromField)
}

/**
 * DBLongtextTypeValue, representing a medium text field in the mysql database.
 * @param value The actual string value and string length > 16,777,215 and <= 4,294,967,295 . (4GB)
 */
case class DBLongtextTypeValue(value: String) extends DBFieldValueType
object DBLongtextTypeValue extends DBTableColumn {
  override type ModelType = DBLongtextTypeValue
  override type FieldType = String

  override def fromModel(model: DBLongtextTypeValue): String = model.value

  override def fromField(field: String): DBLongtextTypeValue = DBLongtextTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBLongtextTypeValue] = MappedColumnType.base[DBLongtextTypeValue, String](fromModel, fromField)
}

/**
 * DBBooleanTypeValue, representing a boolean field in the database.
 *
 * @param value The actual boolean value.
 */
case class DBBooleanTypeValue(value: Boolean) extends DBFieldValueType

object DBBooleanTypeValue extends DBTableColumn {
  override type ModelType = DBBooleanTypeValue
  override type FieldType = Boolean

  override def fromModel(model: DBBooleanTypeValue): Boolean = model.value

  override def fromField(field: Boolean): DBBooleanTypeValue = DBBooleanTypeValue(field)

  override implicit def columnMapper: BaseColumnType[DBBooleanTypeValue] = MappedColumnType.base[DBBooleanTypeValue, Boolean](fromModel, fromField)
}


/**
 * DBJsonTypeValue, representing a json field in the database.
 *
 * @param value The actual json value.
 */
case class DBJsonTypeValue(value: JsonObject) extends DBFieldValueType

object DBJsonTypeValue extends DBTableColumn {
  override type ModelType = DBJsonTypeValue
  override type FieldType = String

  override def fromModel(model: DBJsonTypeValue): String = model.value.toString

  override def fromField(field: String): DBJsonTypeValue = DBJsonTypeValue(JsonObject(field))

  override implicit def columnMapper: BaseColumnType[DBJsonTypeValue] = MappedColumnType.base[DBJsonTypeValue, String](fromModel, fromField)
}

/**
 * DBDateTypeValue, representing a date field in the database.
 *
 * @param value The actual date value.
 */
case class DBDateTypeValue(value: LocalDate) extends DBFieldValueType

object DBDateTypeValue extends DBTableColumn {
  override type ModelType = DBDateTypeValue
  override type FieldType = String

  override def fromModel(model: DBDateTypeValue): String = DateUtils.formatLocalDate(model.value)

  override def fromField(field: String): DBDateTypeValue = DBDateTypeValue(DateUtils.parseLocalDate(field))

  override implicit def columnMapper: BaseColumnType[DBDateTypeValue] = MappedColumnType.base[DBDateTypeValue, String](fromModel, fromField)
}

/**
 * DBDateTimeTypeValue, representing a datetime field in the database.
 *
 * @param value The actual datetime value.
 */
case class DBDateTimeTypeValue(value: LocalDateTime) extends DBFieldValueType

object DBDateTimeTypeValue extends DBTableColumn {
  override type ModelType = DBDateTimeTypeValue
  override type FieldType = String

  override def fromModel(model: DBDateTimeTypeValue): String = DateUtils.formatLocalDateTime(model.value)

  override def fromField(field: String): DBDateTimeTypeValue = DBDateTimeTypeValue(DateUtils.parseLocalDateTime(field))

  override implicit def columnMapper: BaseColumnType[DBDateTimeTypeValue] = MappedColumnType.base[DBDateTimeTypeValue, String](fromModel, fromField)
}

object DBFieldValueType extends Serializable[DBFieldValueType] {

  /**
   * Implicit value that defines the serialization format for type T.
   * This format is a root JSON format, meaning it can serialize and deserialize objects of type T.
   */
  override implicit val serializeFormat: RootJsonFormat[DBFieldValueType] = new RootJsonFormat[DBFieldValueType] {
    override def write(obj: DBFieldValueType): JsValue = obj match {
      case DBIntTypeValue(value) => value.toJson
      case DBLongTypeValue(value) => value.toJson
      case DBFloatTypeValue(value) => value.toJson
      case DBDoubleTypeValue(value) => value.toJson
      case DBStringTypeValue(value) => value.toJson
      case DBTextTypeValue(value) => value.toJson
      case DBMediumtextTypeValue(value) => value.toJson
      case DBLongtextTypeValue(value) => value.toJson
      case DBBooleanTypeValue(value) => value.toJson
      case DBJsonTypeValue(value) => value.toJson
      case DBDateTypeValue(value) => value.toJson
      case DBDateTimeTypeValue(value) => value.toJson
    }

    override def read(json: JsValue): DBFieldValueType = json match {
      case JsNumber(value) =>
        if (value.isValidInt) {
          DBIntTypeValue(value.toInt)
        } else if (value.isValidLong) {
          DBLongTypeValue(value.toLong)
        } else if (value.isDecimalFloat) {
          DBFloatTypeValue(value.toFloat)
        } else if (value.isDecimalFloat) {
          DBDoubleTypeValue(value.toDouble)
        } else {
          throw new IllegalArgumentException(s"Invalid number value: $value")
        }
      case JsString(value) =>
        if (DateUtils.isValidDate(value)) {
          DBDateTypeValue(DateUtils.parseLocalDate(value))
        } else if (DateUtils.isValidDateTime(value)) {
          DBDateTimeTypeValue(DateUtils.parseLocalDateTime(value))
        } else if (JsonUtils.isValidJson(value)) {
          DBJsonTypeValue(JsonObject(value))
        } else if (value.length <= 255) {
          DBStringTypeValue(value)
        } else if (value.length <= 65_535) {
          DBTextTypeValue(value)
        } else if (value.length <= 16_777_215) {
          DBMediumtextTypeValue(value)
        } else {
          DBLongtextTypeValue(value)
        }
      case JsBoolean(value) => DBBooleanTypeValue(value)
      case _ => throw new IllegalArgumentException(s"Expected JsString, JsNumber, JsNumber as DBFieldValue, but received a $json")
    }
  }
}