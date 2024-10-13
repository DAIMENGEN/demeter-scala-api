package com.advantest.demeter.utils.date

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
object DateFormatterUtil {

  private def YYYY_MM_DD: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  private def YYYY_MM_DD_HH_MM_SS: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def currentDate: String = LocalDateTime.now().format(YYYY_MM_DD)

  def currentDateTime: String = LocalDateTime.now().format(YYYY_MM_DD_HH_MM_SS)

  def formatLocalDate(localDate: LocalDate): String = localDate.format(YYYY_MM_DD)

  def formatLocalDateTime(localDateTime: LocalDateTime): String = localDateTime.format(YYYY_MM_DD_HH_MM_SS)

  def parseLocalDate(localDateString: String): LocalDate = LocalDate.parse(localDateString, YYYY_MM_DD)

  def parseLocalDateTime(localDateTimeString: String): LocalDateTime = LocalDateTime.parse(localDateTimeString, YYYY_MM_DD_HH_MM_SS)
}
