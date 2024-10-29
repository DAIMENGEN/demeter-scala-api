package com.advantest.demeter.utils.serialize.format.reader

import com.advantest.demeter.utils.serialize.format.reader.protocol._
import spray.json.{JsValue, JsonFormat}

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * JsonReaderFormat class provides methods for reading values from a JSON object using different `JsonReader` implementations.
 */
case class JsonReaderFormat(values: Map[String, JsValue]) {

  /**
   * Reads a value from a JSON object using a provided `JsonReader`.
   *
   * This method reads a value from the JSON object using the specified `JsonReader`.
   * If reading fails, it throws the encountered exception.
   *
   * @param key         The key to read from the JSON object.
   * @param fieldReader The `JsonReader` instance used to deserialize the value.
   * @return The deserialized value.
   */
  private def read[T](key: String, fieldReader: JsonReader[T]): T = fieldReader.read(key, values) match {
    case Left(exception) => throw exception
    case Right(value) => value
  }

  /**
   * Reads an optional value from a JSON object using a provided `JsonReader`.
   *
   * This method reads an optional value from the JSON object using the specified `JsonReader`.
   * If reading fails, it returns `None`.
   *
   * @param key         The key to read from the JSON object.
   * @param fieldReader The `JsonReader` instance used to deserialize the value.
   * @return An `Option` containing the deserialized value, or `None` if the value is not present.
   */
  private def readOption[T](key: String, fieldReader: JsonReader[T]): Option[T] = fieldReader.readOption(key, values)

  /**
   * Reads a value from a JSON object using the implicit `JsonFormat`.
   *
   * This method reads a value from the JSON object using the implicit `JsonFormat`.
   * If the key is not found, it throws a `DemeterException` with details about the available keys.
   *
   * @param key The key to read from the JSON object.
   * @tparam T The type of the value to read.
   * @return The deserialized value.
   * @throws Exception If the key is not found in the values map.
   */
  def read[T: JsonFormat](key: String): T = values.get(key) match {
    case Some(value) => value.convertTo[T]
    case None => throw new NoSuchElementException(s"$key not found in the values map. Available keys: ${values.keys.mkString(", ")}")
  }

  def readInt(key: String): Int = read(key, IntJsonReader)

  def readOptionInt(key: String): Option[Int] = readOption(key, IntJsonReader)

  def readLong(key: String): Long = read(key, LongJsonReader)

  def readLongArray(key: String): Seq[Long] = read(key, LongArrayJsonReader)

  def readOptionLong(key: String): Option[Long] = readOption(key, LongJsonReader)

  def readString(key: String): String = read(key, StringJsonReader)

  def readOptionString(key: String): Option[String] = readOption(key, StringJsonReader)

  def readBoolean(key: String): Boolean = read(key, BooleanJsonReader)

  def readOptionBoolean(key: String): Option[Boolean] = readOption(key, BooleanJsonReader)

  def readLocalDate(key: String): LocalDate = read(key, LocalDateJsonReader)

  def readOptionLocalDate(key: String): Option[LocalDate] = readOption(key, LocalDateJsonReader)

  def readLocalDateTime(key: String): LocalDateTime = read(key, LocalDateTimeJsonReader)

  def readOptionLocalDateTime(key: String): Option[LocalDateTime] = readOption(key, LocalDateTimeJsonReader)
}
