package com.advantest.demeter

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.advantest.demeter.core.route.{HolidayRoute, UserRoute}
import com.advantest.demeter.utils.database.DBConnection
import com.advantest.demeter.utils.http.HttpRoute
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

  // Configure the database using Slick.
  private lazy implicit val path: String = if (args.apply(0).equals("release")) {
    DEMETER_SYSTEM.log.info("Start with release environment.")
    "demeter-release-mysql"
  } else if (args.apply(0).equals("develop")) {
    DEMETER_SYSTEM.log.info("Start with develop environment.")
    "demeter-develop-mysql"
  } else {
    throw new IllegalArgumentException("Please specify the startup environment (release or develop).")
  }

  // Initialize the global database connection object.
  final lazy implicit val DEMETER_DATABASE: Database = DBConnection.connect

  // Start Demeter Api Server.
  HttpRoute.start(HolidayRoute(), UserRoute())
}
