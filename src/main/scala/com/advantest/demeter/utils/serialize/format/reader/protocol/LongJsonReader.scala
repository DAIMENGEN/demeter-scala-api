package com.advantest.demeter.utils.serialize.format.reader.protocol

import com.advantest.demeter.utils.serialize.format.reader.JsonReader
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.JsValue

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * LongJsonReader: A JsonReader implementation for reading Long value from JSON object.
 */
object LongJsonReader extends JsonReader[Long] {

  /**
   * Reads a value of type Long from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the values.
   * @return Either a DemeterException if an error occurs during extraction, or the extracted value of type Long.
   */
  override def read(key: String, values: Map[String, JsValue]): Either[NoSuchElementException, Long] =
    values.get(key) match {
      case Some(value) => Right(value.convertTo[String].toLong)
      case None => Left(new NoSuchElementException(s"$key not found in the values map. Available keys: ${values.keys.mkString(", ")}"))
    }

  /**
   * Reads an optional value of type Long from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the optional values.
   * @return An Option containing the extracted value of type Long, or None if the value is not present.
   */
  override def readOption(key: String, values: Map[String, JsValue]): Option[Long] =
    values.get(key).flatMap(value => scala.util.Try(value.convertTo[String].toLong).toOption)
}
