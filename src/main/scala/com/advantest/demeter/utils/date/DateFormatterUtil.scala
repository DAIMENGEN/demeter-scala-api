package com.advantest.demeter.utils.date

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * Utility object for formatting and parsing dates and date-times.
 *
 * This object provides methods to format and parse dates in the "yyyy-MM-dd"
 * and "yyyy-MM-dd HH:mm:ss" formats. It also includes methods to get the
 * current date and time formatted as strings.
 */
object DateFormatterUtil {

  private def YYYY_MM_DD: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  private def YYYY_MM_DD_HH_MM_SS: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

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
