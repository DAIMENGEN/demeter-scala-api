package com.advantest.demeter.utils.serialize.format.writer

import com.advantest.demeter.utils.serialize.format.writer.protocol._
import spray.json.{JsObject, JsValue}

import java.time.{LocalDate, LocalDateTime}
import scala.collection.mutable

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * JsonWriterFormat: A utility class for writing JSON object using a fluent API.
 */
case class JsonWriterFormat() {

  private val values = mutable.ListBuffer.empty[Option[(String, JsValue)]]

  /**
   * Writes a value to a JSON object using a provided `JsonWriter`.
   *
   * This method takes a key, a value, and a `JsonWriter` instance, and writes the value
   * to a JSON object.
   *
   * @param key        The key to use in the JSON object.
   * @param value      The value to write.
   * @param jsonWriter The `JsonWriter` instance used to serialize the value.
   * @return A tuple containing the key and the serialized JSON value.
   */
  private def write[T](key: String, value: T, jsonWriter: JsonWriter[T]): (String, JsValue) =
    jsonWriter.write(key, value)

  /**
   * Writes an optional value to a JSON object using a provided `JsonWriter`.
   *
   * This method takes a key, an optional value, and a `JsonWriter` instance, and writes
   * the value to a JSON object if it is present.
   *
   * @param key        The key to use in the JSON object.
   * @param value      The optional value to write.
   * @param jsonWriter The `JsonWriter` instance used to serialize the value.
   * @return An `Option` containing a tuple with the key and the serialized JSON value,
   *         or `None` if the value is `None`.
   */
  private def write[T](key: String, value: Option[T], jsonWriter: JsonWriter[T]): Option[(String, JsValue)] =
    jsonWriter.writeOption(key, value)

  /**
   * Converts the current object to a `JsObject`.
   *
   * This method flattens the internal `values` map and converts it into a `JsObject`.
   *
   * @return A `JsObject` representing the flattened values.
   */
  def toJsObject: JsObject = JsObject(values.flatten.toMap)

  /**
   * Converts the current object to a map of string keys and `JsValue` values.
   *
   * This method flattens the internal `values` map and returns it as a `Map[String, JsValue]`.
   *
   * @return A `Map[String, JsValue]` representing the flattened values.
   */
  def toJsValueMap: Map[String, JsValue] = values.flatten.toMap

  def writeInt(key: String, value: Int): JsonWriterFormat = {
    val tuple2 = write(key, value, IntJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeInt(key: String, value: Option[Int]): JsonWriterFormat = {
    val tuple2 = write(key, value, IntJsonWriter)
    values.addOne(tuple2)
    this
  }

  def writeLong(key: String, value: Long): JsonWriterFormat = {
    val tuple2 = write(key, value, LongJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeLong(key: String, value: Option[Long]): JsonWriterFormat = {
    val tuple2 = write(key, value, LongJsonWriter)
    values.addOne(tuple2)
    this
  }

  def writeFloat(key: String, value: Float): JsonWriterFormat = {
    val tuple2 = write(key, value, FloatJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeFloat(key: String, value: Option[Float]): JsonWriterFormat = {
    val tuple2 = write(key, value, FloatJsonWriter)
    values.addOne(tuple2)
    this
  }

  def writeDouble(key: String, value: Double): JsonWriterFormat = {
    val tuple2 = write(key, value, DoubleJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeDouble(key: String, value: Option[Double]): JsonWriterFormat = {
    val tuple2 = write(key, value, DoubleJsonWriter)
    values.addOne(tuple2)
    this
  }

  def writeString(key: String, value: String): JsonWriterFormat = {
    val tuple2 = write(key, value, StringJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeString(key: String, value: Option[String]): JsonWriterFormat = {
    val tuple2 = write(key, value, StringJsonWriter)
    values.addOne(tuple2)
    this
  }

  def writeBoolean(key: String, value: Boolean): JsonWriterFormat = {
    val tuple2 = write(key, value, BooleanJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeBoolean(key: String, value: Option[Boolean]): JsonWriterFormat = {
    val tuple2 = write(key, value, BooleanJsonWriter)
    values.addOne(tuple2)
    this
  }

  def writeLocalDate(key: String, value: LocalDate): JsonWriterFormat = {
    val tuple2 = write(key, value, LocalDateJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeLocalDate(key: String, value: Option[LocalDate]): JsonWriterFormat = {
    val tuple2 = write(key, value, LocalDateJsonWriter)
    values.addOne(tuple2)
    this
  }

  def writeLocalDateTime(key: String, value: LocalDateTime): JsonWriterFormat = {
    val tuple2 = write(key, value, LocalDateTimeJsonWriter)
    values.addOne(Some(tuple2))
    this
  }

  def writeLocalDateTime(key: String, value: Option[LocalDateTime]): JsonWriterFormat = {
    val tuple2 = write(key, value, LocalDateTimeJsonWriter)
    values.addOne(tuple2)
    this
  }
}
