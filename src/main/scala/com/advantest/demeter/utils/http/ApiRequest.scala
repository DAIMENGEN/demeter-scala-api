package com.advantest.demeter.utils.http

import akka.http.scaladsl.server.{Directive0, Directive1}
import com.advantest.demeter.DemeterScalaApi.{DEMETER_EXECUTION_CONTEXT, DEMETER_SESSION_MANAGER, REFRESH_TOKEN_STORAGE}
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._

/**
 * Create on 2024/10/25
 * Author: mengen.dai@outlook.com
 *
 * Trait representing an API request with session management capabilities.
 */
trait ApiRequest {

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
