package com.advantest.demeter.utils

import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
object DateUtils {

  private val datePattern = raw"\d{4}-\d{2}-\d{2}".r

  private val dateTimePattern = raw"\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}".r

  private val YYYY_MM_DD: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  private val YYYY_MM_DD_HH_MM_SS: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  /**
   * Validates whether the given string matches the expected date pattern.
   *
   * This method checks if the input string conforms to the predefined date pattern.
   * It does not attempt to parse the string as a date or verify its correctness
   * beyond the pattern match.
   *
   * @param dateStr the string to validate as a date
   * @return `true` if the input string matches the expected date pattern; `false` otherwise
   */
  def isValidDate(dateStr: String): Boolean = datePattern.matches(dateStr)

  /**
   * Validates whether the given string represents a valid date in strict format.
   *
   * This method first checks if the input string matches the expected date pattern.
   * If it does not match, it immediately returns `false`. Otherwise, it attempts to
   * parse the string into a LocalDate. If parsing succeeds, the method returns `true`;
   * if parsing fails due to a `DateTimeParseException`, it returns `false`.
   *
   * @param dateStr the string to validate as a date
   * @return `true` if the input string is a valid date in strict format; `false` otherwise
   */
  def isValidDateStrict(dateStr: String): Boolean = {
    if (!datePattern.matches(dateStr)) {
      return false
    }
    try {
      parseLocalDate(dateStr)
      true
    } catch {
      case _: DateTimeParseException => false
    }
  }

  /**
   * Validates whether the given string matches the expected date-time pattern.
   *
   * This method checks if the input string conforms to the predefined date-time pattern.
   * It does not attempt to parse the string as a date-time or verify its correctness
   * beyond the pattern match.
   *
   * @param dateTimeStr the string to validate as a date-time
   * @return `true` if the input string matches the expected date-time pattern; `false` otherwise
   */
  def isValidDateTime(dateTimeStr: String): Boolean = dateTimePattern.matches(dateTimeStr)

  /**
   * Validates whether the given string represents a valid date-time in strict format.
   *
   * This method first checks if the input string matches the expected date-time pattern.
   * If it does not match, it immediately returns `false`. Otherwise, it attempts to
   * parse the string into a LocalDateTime. If parsing succeeds, the method returns `true`;
   * if parsing fails due to a `DateTimeParseException`, it returns `false`.
   *
   * @param dateTimeStr the string to validate as a date-time
   * @return `true` if the input string is a valid date-time in strict format; `false` otherwise
   */
  def isValidDateTimeStrict(dateTimeStr: String): Boolean = {
    if (!dateTimePattern.matches(dateTimeStr)) {
      return false
    }
    try {
      parseLocalDateTime(dateTimeStr)
      true
    } catch {
      case _: DateTimeParseException => false
    }
  }

  /**
   * Returns the current date as a string in the format "yyyy-MM-dd".
   *
   * @return the current date as a string
   */
  def currentDate: String = LocalDateTime.now().format(YYYY_MM_DD)

  /**
   * Returns the current date and time as a string in the format "yyyy-MM-dd HH:mm:ss".
   *
   * @return the current date and time as a string
   */
  def currentDateTime: String = LocalDateTime.now().format(YYYY_MM_DD_HH_MM_SS)

  /**
   * Formats a LocalDate object to a string in the format "yyyy-MM-dd".
   *
   * @param localDate the LocalDate object to format
   * @return the formatted date string
   */
  def formatLocalDate(localDate: LocalDate): String = localDate.format(YYYY_MM_DD)

  /**
   * Formats a LocalDateTime object to a string in the format "yyyy-MM-dd HH:mm:ss".
   *
   * @param localDateTime the LocalDateTime object to format
   * @return the formatted date and time string
   */
  def formatLocalDateTime(localDateTime: LocalDateTime): String = localDateTime.format(YYYY_MM_DD_HH_MM_SS)

  /**
   * Parses a date string in the format "yyyy-MM-dd" to a LocalDate object.
   *
   * @param localDateString the date string to parse
   * @return the parsed LocalDate object
   */
  def parseLocalDate(localDateString: String): LocalDate = LocalDate.parse(localDateString, YYYY_MM_DD)

  /**
   * Parses a date and time string in the format "yyyy-MM-dd HH:mm:ss" to a LocalDateTime object.
   *
   * @param localDateTimeString the date and time string to parse
   * @return the parsed LocalDateTime object
   */
  def parseLocalDateTime(localDateTimeString: String): LocalDateTime = LocalDateTime.parse(localDateTimeString, YYYY_MM_DD_HH_MM_SS)

}
