package com.advantest.demeter.utils.http

import akka.http.scaladsl.server.{Directive0, Directive1}
import com.advantest.demeter.DemeterScalaApi.{DEMETER_EXECUTION_CONTEXT, DEMETER_SESSION_MANAGER, REFRESH_TOKEN_STORAGE}
import com.advantest.demeter.utils.serialize.format.reader.JsonReaderFormat
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import spray.json.{JsValue, JsonFormat}

/**
 * Create on 2024/10/25
 * Author: mengen.dai@outlook.com
 *
 * Trait representing an API request with session management capabilities.
 */
trait ApiRequest {

  type HttpRequestParams = Map[String, JsValue]

  implicit class RichRequestParams(params: HttpRequestParams) {
    private lazy val reader: JsonReaderFormat = JsonReaderFormat(params)

    def read[T: JsonFormat](key: String): T = reader.read[T](key)

    def readInt(key: String): Int = reader.readInt(key)

    def readOptionInt(key: String): Option[Int] = reader.readOptionInt(key)

    def readLong(key: String): Long = reader.readLong(key)

    def readLongArray(key: String): Seq[Long] = reader.readLongArray(key)

    def readOptionLong(key: String): Option[Long] = reader.readOptionLong(key)

    def readString(key: String): String = reader.readString(key)

    def readOptionString(key: String): Option[String] = reader.readOptionString(key)

    def readBoolean(key: String): Boolean = reader.readBoolean(key)

    def readOptionBoolean(key: String): Option[Boolean] = reader.readOptionBoolean(key)
  }

  /**
   * A directive that requires a session to be present in the request.
   * The session is refreshable and uses cookies for session management.
   *
   * @return A Directive1 that extracts a Long value from the session.
   */
  val required: Directive1[Long] = requiredSession(refreshable, usingCookies)

  /**
   * A directive that invalidates the current session.
   * The session is refreshable and uses cookies for session management.
   *
   * @return A Directive0 that does not extract any value but performs the session invalidation.
   */
  val invalidate: Directive0 = invalidateSession(refreshable, usingCookies)
}
