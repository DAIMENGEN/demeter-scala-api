package com.advantest.demeter.utils

import spray.json.JsonParser.ParsingException
import spray.json._

/**
 * Create on 2024/12/19
 * Author: mengen.dai@outlook.com
 */
object JsonUtils {

  /**
   * Validates whether the given string is a valid JSON string.
   *
   * This method attempts to parse the input string using the `spray-json` library's
   * `parseJson` method. If parsing is successful, the string is considered valid JSON.
   * If a `ParsingException` is thrown, it indicates the string is not a valid JSON,
   * and the method returns `false`.
   *
   * @param jsonStr the string to validate as JSON
   * @return `true` if the input string is a valid JSON string; `false` otherwise
   */
  def isValidJson(jsonStr: String): Boolean = {
    try {
      jsonStr.parseJson
      true
    } catch {
      case _: ParsingException => false
    }
  }

}
