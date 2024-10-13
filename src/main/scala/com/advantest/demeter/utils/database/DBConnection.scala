package com.advantest.demeter.utils.database

import akka.Done
import akka.actor.CoordinatedShutdown
import akka.stream.alpakka.slick.scaladsl.SlickSession
import com.advantest.demeter.DemeterScalaApi.DEMETER_SYSTEM
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
object DBConnection {

  /**
   * Connects to the database using the specified configuration path.
   *
   * This method initializes a database connection based on the provided configuration path.
   * It also registers a shutdown hook to ensure that the Slick session is closed gracefully
   * when the system terminates.
   *
   * @param path The configuration path for the database.
   * @return A `Database` instance representing the database connection.
   */
  def connect(implicit path: String): Database = {
    // Load the global database config information.
    val databaseConfig = DatabaseConfig.forConfig[JdbcProfile](path)
    // Initialize the global database connection object.
    val session = SlickSession.forConfig(databaseConfig)
    // Get CoordinatedShutdown object to gracefully handle system shutdown.
    val coordinatedShutdown = CoordinatedShutdown(DEMETER_SYSTEM)
    // Register a shutdown hook to close the Slick session before system termination.
    coordinatedShutdown.addTask(CoordinatedShutdown.PhaseBeforeActorSystemTerminate, "close-session-hook") {
      () => {
        session.close()
        Future.successful(Done)
      }
    }
    session.db
  }
}
