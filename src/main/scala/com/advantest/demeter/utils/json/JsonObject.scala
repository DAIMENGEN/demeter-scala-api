package com.advantest.demeter.utils.json

import com.advantest.demeter.utils.database.DBTableColumn
import com.advantest.demeter.utils.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.DefaultJsonProtocol._
import spray.json._

/**
 * Create on 2024/12/1
 * Author: mengen.dai@outlook.com
 */
final case class JsonObject(value: JsValue) {
  override def toString: String = value.toString()
}

object JsonObject extends DBTableColumn with Serializable[JsonObject] {
  override type ModelType = JsonObject
  override type FieldType = String

  /**
   * Converts a model value to a field value.
   *
   * @param model The model value to convert.
   * @return The corresponding field value.
   */
  override def fromModel(model: JsonObject): String = model.toString

  /**
   * Converts a field value to a model value.
   *
   * @param field The field value to convert.
   * @return The corresponding model value.
   */
  override def fromField(field: String): JsonObject = new JsonObject(field.parseJson)

  /**
   * Provides an implicit column mapper for database interactions.
   *
   * @return An instance of `BaseColumnType` for the given model type.
   */
  override implicit def columnMapper: BaseColumnType[JsonObject] = MappedColumnType.base[JsonObject, String](fromModel, fromField)

  /**
   * Implicit value that defines the serialization format for type T.
   * This format is a root JSON format, meaning it can serialize and deserialize objects of type T.
   */
  override implicit val serializeFormat: RootJsonFormat[JsonObject] = new RootJsonFormat[JsonObject] {
    override def write(obj: JsonObject): JsValue = obj.value

    override def read(json: JsValue): JsonObject = new JsonObject(json)
  }

  def apply(jsonString: String): JsonObject = {
    val result = scala.util.Try(jsonString.parseJson)
    if (result.isFailure) {
      throw new IllegalArgumentException(s"Invalid JSON string: $jsonString")
    }
    new JsonObject(result.get)
  }

  def apply(data: Map[String, JsValue]): JsonObject = {
    new JsonObject(data.toJson)
  }
}
