package com.advantest.demeter

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.advantest.demeter.core.route.{HolidayRoute, EmployeeRoute}
import com.advantest.demeter.utils.database.DBConnection
import com.advantest.demeter.utils.http.HttpRoute
import com.softwaremill.session._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContextExecutor

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
object DemeterScalaApi extends App {

  // Create an ActorSystem for managing actors.
  final lazy implicit val DEMETER_SYSTEM: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "DEMETER_SYSTEM")

  // Create an ExecutionContext for handling asynchronous operations.
  final lazy implicit val DEMETER_EXECUTION_CONTEXT: ExecutionContextExecutor = DEMETER_SYSTEM.executionContext

  // This instance is initialized by reading the configuration from application.conf.
  private final val DEMETER_SESSION_CONFIG: SessionConfig = SessionConfig.fromConfig()

  // Create a SessionManager instance for managing session data.
  private implicit val DEMETER_SESSION_ENCODER: BasicSessionEncoder[Long] = new BasicSessionEncoder[Long]()
  final implicit val DEMETER_SESSION_MANAGER: SessionManager[Long] = new SessionManager[Long](DEMETER_SESSION_CONFIG)
  final implicit val REFRESH_TOKEN_STORAGE: InMemoryRefreshTokenStorage[Long] = (msg: String) => DEMETER_SYSTEM.log.info(msg)

  // Configure the database using Slick.
  private lazy implicit val path: String = if (args.apply(0).equals("release")) {
    DEMETER_SYSTEM.log.info("Start with release environment.")
    "database.release"
  } else if (args.apply(0).equals("develop")) {
    DEMETER_SYSTEM.log.info("Start with develop environment.")
    "database.develop"
  } else {
    throw new IllegalArgumentException("Please specify the startup environment (release or develop).")
  }

  // Initialize the global database connection object.
  final lazy implicit val DEMETER_DATABASE: Database = DBConnection.connect

  // Start Demeter Api Server.
  HttpRoute.start(HolidayRoute(), EmployeeRoute())
}
