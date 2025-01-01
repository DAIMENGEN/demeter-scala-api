package com.advantest.demeter.database

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.json.serialize.Serializable
import spray.json.{JsString, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
sealed trait DBFieldType

/**
 * DBIntFieldType, representing an int field type in the database.
 * DBIntFieldType is mapped to scala.Int in the database.
 */
case object DBIntFieldType extends DBFieldType

/**
 * DBLongFieldType, representing a long field type in the database.
 * DBLongFieldType is mapped to scala.Long in the database.
 */
case object DBLongFieldType extends DBFieldType

/**
 * DBFloatFieldType, representing a float field type in the database.
 * DBFloatFieldType is mapped to scala.Float in the database.
 */
case object DBFloatFieldType extends DBFieldType

/**
 * DBDoubleFieldType, representing a double field type in the database.
 * DBDoubleFieldType is mapped to scala.Double in the database.
 */
case object DBDoubleFieldType extends DBFieldType

/**
 * DBStringFieldType, representing a string field type in the database.
 * DBStringFieldType is mapped to scala.String in the database.
 */
case object DBStringFieldType extends DBFieldType

/**
 * DBTextFieldType, representing a text field type in the database.
 * DBTextFieldType is mapped to scala.String in the database.
 */
case object DBTextFieldType extends DBFieldType

/**
 * DBMediumtextFieldType, representing a mediumtext field type in the database.
 * DBMediumtextFieldType is mapped to scala.String in the database.
 */
case object DBMediumtextFieldType extends DBFieldType

/**
 * DBLongtextFieldType, representing a longtext field type in the database.
 * DBLongtextFieldType is mapped to scala.String in the database.
 */
case object DBLongtextFieldType extends DBFieldType

/**
 * DBBooleanFieldType, representing a boolean field type in the database.
 * DBBooleanFieldType is mapped to scala.Boolean in the database.
 */
case object DBBooleanFieldType extends DBFieldType

/**
 * DBJsonFieldType, representing a json field type in the database.
 * DBJsonFieldType is mapped to spray.json.JsValue in the database.
 */
case object DBJsonFieldType extends DBFieldType

/**
 * DBDateFieldType, representing a date field type in the database.
 * DBDateFieldType is mapped to java.time.LocalDate in the database.
 */
case object DBDateFieldType extends DBFieldType

/**
 * DBDateTimeFieldType, representing a datetime field type in the database.
 * DBDateTimeFieldType is mapped to java.time.LocalDateTime in the database.
 */
case object DBDateTimeFieldType extends DBFieldType

object DBFieldType extends DBTableColumn with Serializable[DBFieldType] {
  override type ModelType = DBFieldType
  override type FieldType = String

  override def fromModel(model: DBFieldType): String = model match {
    case DBIntFieldType => "int"
    case DBTextFieldType => "text"
    case DBJsonFieldType => "json"
    case DBDateFieldType => "date"
    case DBLongFieldType => "bigint"
    case DBFloatFieldType => "float"
    case DBDoubleFieldType => "double"
    case DBStringFieldType => "varchar"
    case DBBooleanFieldType => "boolean"
    case DBDateTimeFieldType => "datetime"
    case DBLongtextFieldType => "longtext"
    case DBMediumtextFieldType => "mediumtext"
  }

  override def fromField(field: String): DBFieldType = field match {
    case "int" => DBIntFieldType
    case "text" => DBTextFieldType
    case "date" => DBDateFieldType
    case "json" => DBJsonFieldType
    case "bigint" => DBLongFieldType
    case "float" => DBFloatFieldType
    case "double" => DBDoubleFieldType
    case "varchar" => DBStringFieldType
    case "boolean" => DBBooleanFieldType
    case "datetime" => DBDateTimeFieldType
    case "longtext" => DBLongtextFieldType
    case "mediumtext" => DBMediumtextFieldType
    case _ => throw new IllegalArgumentException(s"Invalid DBFieldType field: $field. Valid fields are 'int', 'text', 'json', 'date', 'bigint', 'float', 'double', 'varchar', 'boolean', 'datetime', 'longtext', 'mediumtext'.")
  }

  override implicit def columnMapper: BaseColumnType[DBFieldType] = MappedColumnType.base[DBFieldType, String](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[DBFieldType] = new RootJsonFormat[DBFieldType] {
    override def write(obj: DBFieldType): JsValue = JsString(fromModel(obj))

    override def read(json: JsValue): DBFieldType = json match {
      case JsString(value) => fromField(value)
      case _ => throw new IllegalArgumentException("Expected a JsString for DBFieldType, but received a different type of JsValue.")
    }
  }
}