package com.advantest.demeter.utils.serialize.format.writer.protocol

import com.advantest.demeter.utils.serialize.format.writer.JsonWriter
import spray.json.{JsBoolean, JsValue}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
object BooleanJsonWriter extends JsonWriter[Boolean] {

  /**
   * Writes a value of type Boolean into a JSON field with the specified key.
   *
   * @param key   The key for the JSON field.
   * @param value The value to be converted into JSON format.
   * @return A tuple containing the key-value pair in JSON format.
   */
  override def write(key: String, value: Boolean): (String, JsValue) = (key, JsBoolean(value))

  /**
   * Writes an optional value of type Boolean into a JSON field with the specified key.
   *
   * @param key    The key for the JSON field.
   * @param option An Option containing the value to be converted into JSON format, or None.
   * @return An Option containing a tuple with the key-value pair in JSON format, or None if the option is None.
   */
  override def writeOption(key: String, option: Option[Boolean]): Option[(String, JsValue)] = option.map(value => (key, JsBoolean(value)))
}
