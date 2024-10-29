package com.advantest.demeter.utils.database

import com.advantest.demeter.utils.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.{JsString, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
sealed trait DBFieldType

/**
 * IntType, representing an int field type in the database.
 * IntType is mapped to scala.Int in the database.
 */
case object IntType extends DBFieldType

/**
 * LongType, representing a long field type in the database.
 * LongType is mapped to scala.Long in the database.
 */
case object LongType extends DBFieldType

/**
 * TextType, representing a text field type in the database.
 * TextType is mapped to scala.String in the database.
 */
case object TextType extends DBFieldType

/**
 * FloatType, representing a float field type in the database.
 * FloatType is mapped to scala.Float in the database.
 */
case object FloatType extends DBFieldType

/**
 * DoubleType, representing a double field type in the database.
 * DoubleType is mapped to scala.Double in the database.
 */
case object DoubleType extends DBFieldType

/**
 * StringType, representing a string field type in the database.
 * StringType is mapped to scala.String in the database.
 */
case object StringType extends DBFieldType

/**
 * BooleanType, representing a boolean field type in the database.
 * BooleanType is mapped to scala.Boolean in the database.
 */
case object BooleanType extends DBFieldType

/**
 * DateType, representing a date field type in the database.
 * DateType is mapped to java.time.LocalDate in the database.
 */
case object DateType extends DBFieldType

/**
 * DateTimeType, representing a datetime field type in the database.
 * DateTimeType is mapped to java.time.LocalDateTime in the database.
 */
case object DateTimeType extends DBFieldType

object DBFieldType extends DBTableColumn with Serializable[DBFieldType] {
  override type ModelType = DBFieldType
  override type FieldType = String

  override def fromModel(model: DBFieldType): String = model match {
    case IntType => "int"
    case TextType => "text"
    case LongType => "bigint"
    case FloatType => "float"
    case DoubleType => "double"
    case StringType => "varchar"
    case BooleanType => "boolean"
    case DateType => "date"
    case DateTimeType => "datetime"
  }

  override def fromField(field: String): DBFieldType = field match {
    case "int" => IntType
    case "text" => TextType
    case "date" => DateType
    case "bigint" => LongType
    case "float" => FloatType
    case "double" => DoubleType
    case "varchar" => StringType
    case "boolean" => BooleanType
    case "datetime" => DateTimeType
    case _ => throw new IllegalArgumentException(s"Invalid DBFieldType field: $field. Valid fields are 'int', 'text', 'bigint', 'float', 'double', 'varchar', 'boolean', 'date', 'datetime'.")
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