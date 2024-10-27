package com.advantest.demeter.utils.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives.{complete, onComplete}
import akka.http.scaladsl.server.{RequestContext, RouteResult}
import com.advantest.demeter.DemeterScalaApi.{DEMETER_JWT_ALGORITHM, DEMETER_JWT_ISSUER, DEMETER_JWT_KEY_ID, DEMETER_JWT_SECRET}
import com.advantest.demeter.core.entity.EmployeeEntity
import pdi.jwt.{Jwt, JwtClaim}
import spray.json.{JsonFormat, enrichAny}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait ApiResponse extends SprayJsonSupport {

  /**
   * A utility function to handle asynchronous responses in a Spray (Akka HTTP) route.
   *
   * @param future A Future[T] representing the asynchronous operation.
   * @tparam T The type of the expected result.
   * @return A function that takes a RequestContext and returns a Future[RouteResult].
   */
  def response[T: JsonFormat](future: => Future[T]): RequestContext => Future[RouteResult] = {
    onComplete(future) {
      case Success(value) => complete(ApiResponseModel.success(value))
      case Failure(exception) => complete(ApiResponseModel.failure(exception.getMessage))
    }
  }

  /**
   * Generates an authentication token for an employee.
   *
   * This method generates a JWT token based on the employee information, which is valid for a certain period of time and is used for employee authentication.
   *
   * @param employee The employee entity containing relevant employee information.
   * @return A string representing the generated JWT token.
   */
  def generateToken(employee: EmployeeEntity): String = {
    // Create a JWT claim with an expiration time and employee information
    val claim = JwtClaim(
      expiration = Some(System.currentTimeMillis() + 3600000),
      content = employee.toJson.compactPrint,
      issuer = Some(DEMETER_JWT_ISSUER),
      jwtId = Some(DEMETER_JWT_KEY_ID)
    )
    // Encode the claim using HS256 algorithm and a secret key to generate the token
    Jwt.encode(claim, DEMETER_JWT_SECRET, DEMETER_JWT_ALGORITHM)
  }
}
