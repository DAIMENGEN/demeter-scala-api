package com.advantest.demeter.utils.http

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.concat
import akka.http.scaladsl.server.Route
import com.advantest.demeter.DemeterScalaApi.{DEMETER_EXECUTION_CONTEXT, DEMETER_SYSTEM}
import com.advantest.demeter.utils.serialize.format.reader.JsonReaderFormat
import spray.json.{JsValue, JsonFormat}

import scala.util.{Failure, Success}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait HttpRoute {
  type HttpRequestParams = Map[String, JsValue]

  def route: Route

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
}

object HttpRoute {

  private val DEFAULT_IP = "0.0.0.0"

  private val DEFAULT_PORT = 8080

  def start(IP: String, PORT: Int, routes: HttpRoute*): Unit = {
    // Start the HTTP server and bind it to the specified address and port.
    val binding = Http().newServerAt(IP, PORT).bind(concat(routes.map(_.route): _*))
    // Handle the result of server binding.
    binding onComplete {
      case Failure(e) =>
        // If server start fails, print an error message and terminate the system.
        DEMETER_SYSTEM.log.info(s"Server start failed due to: ${e.getMessage}. Cause: ${Option(e.getCause).map(_.getMessage).getOrElse("Unknown cause")}")
        DEMETER_SYSTEM.terminate()
      case Success(_) =>
        // If server starts successfully, print a success message with the server URL.
        DEMETER_SYSTEM.log.info(s"Server started successfully at $IP:$PORT")
    }
  }

  def start(routes: HttpRoute*): Unit = start(DEFAULT_IP, DEFAULT_PORT, routes: _*)
}
