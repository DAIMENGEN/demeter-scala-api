package com.advantest.demeter.utils.serialize

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.advantest.demeter.utils.date.DateFormatterUtil
import spray.json.{JsString, JsValue, RootJsonFormat}

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
   * Implicit JSON format for `LocalDate`.
   * Provides methods to serialize and deserialize `LocalDate` objects to and from JSON strings.
   */
  implicit val localDateFormat: RootJsonFormat[LocalDate] = new RootJsonFormat[LocalDate] {

    /**
     * Serializes a `LocalDate` object to a JSON string.
     *
     * @param date The `LocalDate` object to be serialized
     * @return A JSON string representing the `LocalDate` object
     */
    override def write(date: LocalDate): JsValue = {
      JsString(DateFormatterUtil.formatLocalDate(date))
    }

    /**
     * Deserializes a JSON string to a `LocalDate` object.
     *
     * @param json The JSON value to be deserialized
     * @return A `LocalDate` object
     * @throws IllegalArgumentException if the JSON value is not a string
     */
    override def read(json: JsValue): LocalDate = json match {
      case JsString(value) => DateFormatterUtil.parseLocalDate(value)
      case _ => throw new IllegalArgumentException(s"Expected a string, but received ${json.getClass.getSimpleName}.")
    }
  }

  /**
   * Implicit JSON format for `LocalDateTime`.
   * Provides methods to serialize and deserialize `LocalDateTime` objects to and from JSON strings.
   */
  implicit val localDateTimeFormat: RootJsonFormat[LocalDateTime] = new RootJsonFormat[LocalDateTime] {

    /**
     * Serializes a `LocalDateTime` object to a JSON string.
     *
     * @param dateTime The `LocalDateTime` object to be serialized
     * @return A JSON string representing the `LocalDateTime` object
     */
    override def write(dateTime: LocalDateTime): JsValue = {
      JsString(DateFormatterUtil.formatLocalDateTime(dateTime))
    }

    /**
     * Deserializes a JSON string to a `LocalDateTime` object.
     *
     * @param json The JSON value to be deserialized
     * @return A `LocalDateTime` object
     * @throws IllegalArgumentException if the JSON value is not a string
     */
    override def read(json: JsValue): LocalDateTime = json match {
      case JsString(value) => DateFormatterUtil.parseLocalDateTime(value)
      case _ => throw new IllegalArgumentException(s"Serialization Failure: Expected a string, but received ${json.getClass.getSimpleName}.")
    }
  }
}

