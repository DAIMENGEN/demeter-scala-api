package com.advantest.demeter.utils.serialize.format.reader

import spray.json.JsValue

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait JsonReader[T] {

  /**
   * Reads a value of type `T` from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the values.
   * @return Either a DemeterException if an error occurs during extraction, or the extracted value of type `T`.
   */
  def read(key: String, values: Map[String, JsValue]): Either[NoSuchElementException, T]

  /**
   * Reads an optional value of type `T` from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the optional values.
   * @return An Option containing the extracted value of type `T`, or None if the value is not present.
   */
  def readOption(key: String, values: Map[String, JsValue]): Option[T]
}
