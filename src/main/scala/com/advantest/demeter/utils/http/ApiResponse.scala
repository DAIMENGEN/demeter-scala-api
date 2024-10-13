package com.advantest.demeter.utils.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives.{complete, onComplete}
import akka.http.scaladsl.server.{RequestContext, RouteResult}
import spray.json.JsonFormat

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
}
