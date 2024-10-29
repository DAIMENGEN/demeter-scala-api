package com.advantest.demeter.utils.serialize.format.writer.protocol

import com.advantest.demeter.utils.serialize.format.writer.JsonWriter
import spray.json.{JsString, JsValue}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * StringJsonWriter: A JsonWriter implementation for writing String value into JSON object.
 */
object StringJsonWriter extends JsonWriter[String] {

  /**
   * Writes a value of type `String` into a JSON field with the specified key.
   *
   * @param key   The key for the JSON field.
   * @param value The value to be converted into JSON format.
   * @return A tuple containing the key-value pair in JSON format.
   */
  override def write(key: String, value: String): (String, JsValue) = (key, JsString(value))

  /**
   * Writes an optional value of type `String` into a JSON field with the specified key.
   *
   * @param key    The key for the JSON field.
   * @param option An Option containing the value to be converted into JSON format, or None.
   * @return An Option containing a tuple with the key-value pair in JSON format, or None if the option is None.
   */
  override def writeOption(key: String, option: Option[String]): Option[(String, JsValue)] = option.map(value => (key, JsString(value)))
}
