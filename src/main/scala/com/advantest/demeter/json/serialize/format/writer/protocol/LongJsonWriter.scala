package com.advantest.demeter.json.serialize.format.writer.protocol

import com.advantest.demeter.json.serialize.format.writer.JsonWriter
import spray.json._

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * LongJsonWriter: A JsonWriter implementation for writing Long value into JSON object.
 */
object LongJsonWriter extends JsonWriter[Long] {

  /**
   * Writes a value of type `Long` into a JSON field with the specified key.
   *
   * @param key   The key for the JSON field.
   * @param value The value to be converted into JSON format.
   * @return A tuple containing the key-value pair in JSON format.
   */
  override def write(key: String, value: Long): (String, JsValue) = (key, JsString(value.toString))

  /**
   * Writes an optional value of type `Long` into a JSON field with the specified key.
   *
   * @param key    The key for the JSON field.
   * @param option An Option containing the value to be converted into JSON format, or None.
   * @return An Option containing a tuple with the key-value pair in JSON format, or None if the option is None.
   */
  override def writeOption(key: String, option: Option[Long]): Option[(String, JsValue)] = option.map(value => (key, JsString(value.toString)))
}
