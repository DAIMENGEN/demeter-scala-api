package com.advantest.demeter.utils.serialize

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.advantest.demeter.utils.date.DateFormatterUtil
import spray.json.{JsNumber, JsString, JsValue, JsonFormat}

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * A trait that extends SprayJsonSupport to define default serialization protocols for date and date-time types.
 * It provides implicit JSON formats for `LocalDate` and `LocalDateTime` to enable their serialization and deserialization.
 */
trait DefaultSerializeProtocol extends SprayJsonSupport {

  /**
   * Implicit JSON format for `Long`.
   * Provides methods to serialize and deserialize `Long` values to and from JSON strings.
   */
  implicit object LongJsonFormat extends JsonFormat[Long] {

    /**
     * Serializes a `Long` value to a JSON string.
     *
     * @param obj The `Long` value to be serialized.
     * @return A JSON string representing the `Long` value.
     */
    override def write(obj: Long): JsValue = JsString(obj.toString)

    /**
     * Deserializes a JSON string to a `Long` value.
     *
     * @param json The JSON value to be deserialized.
     * @return A `Long` value.
     * @throws IllegalArgumentException if the JSON value is not a string or a number.
     */
    override def read(json: JsValue): Long = json match {
      case JsString(value) => value.toLong
      case JsNumber(value) => value.toLong
      case _ => throw new IllegalArgumentException(s"Expected a string as a Long, but received ${json.getClass.getSimpleName}.")
    }
  }

  /**
   * Implicit JSON format for `LocalDate`.
   * Provides methods to serialize and deserialize `LocalDate` objects to and from JSON strings.
   */
  implicit object LocalDateJsonFormat extends JsonFormat[LocalDate] {

    /**
     * Serializes a `LocalDate` object to a JSON string.
     *
     * @param obj The `LocalDate` object to be serialized.
     * @return A JSON string representing the `LocalDate` object.
     */
    override def write(obj: LocalDate): JsValue = JsString(DateFormatterUtil.formatLocalDate(obj))

    /**
     * Deserializes a JSON string to a `LocalDate` object.
     *
     * @param json The JSON value to be deserialized.
     * @return A `LocalDate` object.
     * @throws IllegalArgumentException if the JSON value is not a string.
     */
    override def read(json: JsValue): LocalDate = json match {
      case JsString(value) => DateFormatterUtil.parseLocalDate(value)
      case _ => throw new IllegalArgumentException(s"Expected a string as a LocalDate, but received ${json.getClass.getSimpleName}.")
    }
  }

  /**
   * Implicit JSON format for `LocalDateTime`.
   * Provides methods to serialize and deserialize `LocalDateTime` objects to and from JSON strings.
   */
  implicit object LocalDateTimeJsonFormat extends JsonFormat[LocalDateTime] {

    /**
     * Serializes a `LocalDateTime` object to a JSON string.
     *
     * @param obj The `LocalDateTime` object to be serialized.
     * @return A JSON string representing the `LocalDateTime` object.
     */
    override def write(obj: LocalDateTime): JsValue = JsString(DateFormatterUtil.formatLocalDateTime(obj))

    /**
     * Deserializes a JSON string to a `LocalDateTime` object.
     *
     * @param json The JSON value to be deserialized.
     * @return A `LocalDateTime` object.
     * @throws IllegalArgumentException if the JSON value is not a string.
     */
    override def read(json: JsValue): LocalDateTime = json match {
      case JsString(value) => DateFormatterUtil.parseLocalDateTime(value)
      case _ => throw new IllegalArgumentException(s"Serialization Failure: Expected a string as a LocalDateTime, but received ${json.getClass.getSimpleName}.")
    }
  }
}

