package com.advantest.demeter.utils.http

import akka.http.scaladsl.server.Directives.{extractRequest, provide, reject}
import akka.http.scaladsl.server.{AuthorizationFailedRejection, Directive1}
import com.advantest.demeter.DemeterScalaApi.{DEMETER_JWT_ALGORITHM, DEMETER_JWT_SECRET}
import com.advantest.demeter.core.entity.EmployeeEntity
import com.advantest.demeter.utils.serialize.format.reader.JsonReaderFormat
import pdi.jwt.Jwt
import spray.json._

import scala.util.{Failure, Success}

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
   * Validates the authenticity of an authentication token and returns the employee information.
   *
   * This method decodes the JWT token, verifies its validity, and extracts the employee information from the token. If the token is invalid or expired, it returns None.
   *
   * @return An Option containing the employee information if the token is valid; otherwise, None.
   */
  def validateToken: Directive1[EmployeeEntity] = {
    extractRequest.flatMap { request =>
      request.header("Authorization") match {
        case Some(authHeader) =>
          val token = authHeader.value().stripPrefix("Bearer ")
          // Decode the JWT token and verify its validity
          Jwt.decode(token, DEMETER_JWT_SECRET, Seq(DEMETER_JWT_ALGORITHM)) match {
            // If decoding fails, the token is invalid or expired, return None
            case Failure(_) => reject(AuthorizationFailedRejection)
            // If decoding succeeds, extract and return the employee information
            case Success(value) => provide(value.content.parseJson.convertTo[EmployeeEntity])
          }
        case None => reject(AuthorizationFailedRejection)
      }
    }
  }
}
