package com.advantest.demeter.json.serialize.format.reader.protocol

import com.advantest.demeter.json.serialize.format.reader.JsonReader
import spray.json.DefaultJsonProtocol.DoubleJsonFormat
import spray.json.JsValue

/**
 * Create on 2024/12/21
 * Author: mengen.dai@outlook.com
 *
 * DoubleJsonReader: A JsonReader implementation for reading Double value from JSON object.
 */
object DoubleJsonReader extends JsonReader[Double] {

  /**
   * Reads a value of type Double from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the values.
   * @return Either a DemeterException if an error occurs during extraction, or the extracted value of type Double.
   */
  override def read(key: String, values: Map[String, JsValue]): Either[NoSuchElementException, Double] =
    values.get(key) match {
      case Some(value) => Right(value.convertTo[Double])
      case None => Left(new NoSuchElementException(s"$key not found in the values map. Available keys: ${values.keys.mkString(", ")}"))
    }

  /**
   * Reads an optional value of type Double from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the optional values.
   * @return An Option containing the extracted value of type Double, or None if the value is not present.
   */
  override def readOption(key: String, values: Map[String, JsValue]): Option[Double] =
    values.get(key).flatMap(value => scala.util.Try(value.convertTo[Double]).toOption)
}
