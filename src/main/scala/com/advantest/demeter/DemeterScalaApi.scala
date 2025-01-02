package com.advantest.demeter

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.advantest.demeter.http.HttpRoute
import com.advantest.demeter.http.route._
import com.typesafe.config.{ConfigFactory, ConfigObject}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

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

  // Load configuration from the application.conf file.
  private final lazy val DEMETER_CONFIG = ConfigFactory.load()
  private final lazy val DEMETER_JWT_SECRETS = DEMETER_CONFIG.getList("akka.http.jwt.secrets")
  private final lazy val DEMETER_JWT_SECRET_CONFIG = DEMETER_JWT_SECRETS.get(0).asInstanceOf[ConfigObject].toConfig
  final lazy val DEMETER_JWT_ALGORITHM = pdi.jwt.JwtAlgorithm.HS256
  final lazy val DEMETER_JWT_SECRET = DEMETER_JWT_SECRET_CONFIG.getString("secret")
  final lazy val DEMETER_JWT_ISSUER = DEMETER_JWT_SECRET_CONFIG.getString("issuer")
  final lazy val DEMETER_JWT_KEY_ID = DEMETER_JWT_SECRET_CONFIG.getString("key-id")


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

  // Configure the database using Slick.
  val DATABASE_CONFIG: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile](path)
  final lazy implicit val database: DATABASE_CONFIG.profile.backend.Database = DATABASE_CONFIG.db

  // Start Demeter Api Server.
  HttpRoute.start(HolidayRoute(), EmployeeRoute(), DepartmentRoute(), TeamRoute(), ProjectRoute())
}
