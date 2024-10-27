package com.advantest.demeter.utils.http

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{concat, cors}
import akka.http.scaladsl.server.Route
import com.advantest.demeter.DemeterScalaApi.{DEMETER_EXECUTION_CONTEXT, DEMETER_SYSTEM}

import scala.util.{Failure, Success}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait HttpRoute {
  def route: Route
}

object HttpRoute {

  private val DEFAULT_IP = "0.0.0.0"

  private val DEFAULT_PORT = 9090

  def start(IP: String, PORT: Int, routes: HttpRoute*): Unit = {
    // Start the HTTP server and bind it to the specified address and port.
    val binding = Http().newServerAt(IP, PORT).bind(cors() {
      concat(routes.map(_.route): _*)
    })
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
