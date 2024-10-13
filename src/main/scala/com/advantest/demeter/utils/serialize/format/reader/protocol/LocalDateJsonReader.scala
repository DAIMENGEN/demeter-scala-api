package com.advantest.demeter.utils.serialize.format.reader.protocol

import com.advantest.demeter.utils.date.DateFormatterUtil
import com.advantest.demeter.utils.serialize.format.reader.JsonReader
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.{JsString, JsValue}

import java.time.LocalDate
import scala.util.Try

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
object LocalDateJsonReader extends JsonReader[LocalDate] {

  /**
   * Reads a value of type LocalDate from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the values.
   * @return Either a DemeterException if an error occurs during extraction, or the extracted value of type LocalDate.
   */
  override def read(key: String, values: Map[String, JsValue]): Either[NoSuchElementException, LocalDate] =
    values.get(key) match {
      case Some(JsString(value)) => Right(DateFormatterUtil.parseLocalDate(value))
      case _ => Left(new NoSuchElementException(s"$key not found in the values map. Available keys: ${values.keys.mkString(", ")}"))
    }

  /**
   * Reads an optional value of type LocalDate from the provided JSON object based on the specified key.
   *
   * @param key    The key used to retrieve the value from the JSON object.
   * @param values The JSON object containing the optional values.
   * @return An Option containing the extracted value of type LocalDate, or None if the value is not present.
   */
  override def readOption(key: String, values: Map[String, JsValue]): Option[LocalDate] =
    values.get(key).flatMap(value => Try(DateFormatterUtil.parseLocalDate(value.convertTo[String])).toOption)
}
