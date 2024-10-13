package com.advantest.demeter.utils.http

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
sealed trait HttpStatusCode
case object OK extends HttpStatusCode
case object INTERNAL_SERVER_ERROR extends HttpStatusCode

object HttpStatusCode {
  def toCode(statusCode: HttpStatusCode): Int = statusCode match {
    case OK => 200
    case INTERNAL_SERVER_ERROR => 500
  }
}