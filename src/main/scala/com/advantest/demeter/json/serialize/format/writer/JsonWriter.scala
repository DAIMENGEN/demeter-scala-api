package com.advantest.demeter.json.serialize.format.writer

import spray.json.JsValue

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * JsonWriter: A trait representing a writer for converting value of type `T` into JSON format.
 */
trait JsonWriter[T] {

  /**
   * Writes a value of type `T` into a JSON field with the specified key.
   *
   * @param key   The key for the JSON field.
   * @param value The value to be converted into JSON format.
   * @return A tuple containing the key-value pair in JSON format.
   */
  def write(key: String, value: T): (String, JsValue)

  /**
   * Writes an optional value of type `T` into a JSON field with the specified key.
   *
   * @param key    The key for the JSON field.
   * @param option An Option containing the value to be converted into JSON format, or None.
   * @return An Option containing a tuple with the key-value pair in JSON format, or None if the option is None.
   */
  def writeOption(key: String, option: Option[T]): Option[(String, JsValue)]
}
