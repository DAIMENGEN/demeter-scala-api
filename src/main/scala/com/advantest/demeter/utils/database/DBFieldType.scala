package com.advantest.demeter.utils.database

import com.advantest.demeter.utils.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.{JsString, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
sealed trait DBFieldType

case object IntType extends DBFieldType

case object LongType extends DBFieldType

case object TextType extends DBFieldType

case object FloatType extends DBFieldType

case object DoubleType extends DBFieldType

case object StringType extends DBFieldType

case object BooleanType extends DBFieldType

case object DateType extends DBFieldType

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