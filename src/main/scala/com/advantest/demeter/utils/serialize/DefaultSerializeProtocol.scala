package com.advantest.demeter.utils.serialize

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.advantest.demeter.utils.date.DateFormatterUtil
import spray.json.{JsString, JsValue, RootJsonFormat}

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait DefaultSerializeProtocol extends SprayJsonSupport {

  implicit val localDateFormat: RootJsonFormat[LocalDate] = new RootJsonFormat[LocalDate] {
    override def write(date: LocalDate): JsValue = {
      JsString(DateFormatterUtil.formatLocalDate(date))
    }

    override def read(json: JsValue): LocalDate = json match {
      case JsString(value) => DateFormatterUtil.parseLocalDate(value)
      case _ => throw new IllegalArgumentException(s"Expected a string, but received ${json.getClass.getSimpleName}.")
    }
  }

  implicit val localDateTimeFormat: RootJsonFormat[LocalDateTime] = new RootJsonFormat[LocalDateTime] {
    override def write(dateTime: LocalDateTime): JsValue = {
      JsString(DateFormatterUtil.formatLocalDateTime(dateTime))
    }

    override def read(json: JsValue): LocalDateTime = json match {
      case JsString(value) => DateFormatterUtil.parseLocalDateTime(value)
      case _ => throw new IllegalArgumentException(s"Serialization Failure: Expected a string, but received ${json.getClass.getSimpleName}.")
    }
  }
}
