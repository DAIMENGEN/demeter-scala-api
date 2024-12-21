package com.advantest.demeter.utils.serialize.format.writer.protocol

import com.advantest.demeter.utils.serialize.format.writer.JsonWriter
import spray.json._

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 *
 * FloatJsonWriter: A JsonWriter implementation for writing Float value into JSON object.
 */
object FloatJsonWriter extends JsonWriter[Float] {

  /**
   * Writes a value of type `Float` into a JSON field with the specified key.
   *
   * @param key   The key for the JSON field.
   * @param value The value to be converted into JSON format.
   * @return A tuple containing the key-value pair in JSON format.
   */
  override def write(key: String, value: Float): (String, JsValue) = (key, JsNumber(value))

  /**
   * Writes an optional value of type `Float` into a JSON field with the specified key.
   *
   * @param key    The key for the JSON field.
   * @param option An Option containing the value to be converted into JSON format, or None.
   * @return An Option containing a tuple with the key-value pair in JSON format, or None if the option is None.
   */
  override def writeOption(key: String, option: Option[Float]): Option[(String, JsValue)] = option.map(value => (key, JsNumber(value)))
}
