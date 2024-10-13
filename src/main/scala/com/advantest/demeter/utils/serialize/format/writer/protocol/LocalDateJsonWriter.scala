package com.advantest.demeter.utils.serialize.format.writer.protocol

import com.advantest.demeter.utils.date.DateFormatterUtil
import com.advantest.demeter.utils.serialize.format.writer.JsonWriter
import spray.json.{JsString, JsValue}

import java.time.LocalDate

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
object LocalDateJsonWriter extends JsonWriter[LocalDate] {

  /**
   * Writes a value of type `LocalDate` into a JSON field with the specified key.
   *
   * @param key   The key for the JSON field.
   * @param value The value to be converted into JSON format.
   * @return A tuple containing the key-value pair in JSON format.
   */
  override def write(key: String, value: LocalDate): (String, JsValue) =
    (key, JsString(DateFormatterUtil.formatLocalDate(value)))

  /**
   * Writes an optional value of type `LocalDate` into a JSON field with the specified key.
   *
   * @param key    The key for the JSON field.
   * @param option An Option containing the value to be converted into JSON format, or None.
   * @return An Option containing a tuple with the key-value pair in JSON format, or None if the option is None.
   */
  override def writeOption(key: String, option: Option[LocalDate]): Option[(String, JsValue)] =
    option.map(value => (key, JsString(DateFormatterUtil.formatLocalDate(value))))
}
