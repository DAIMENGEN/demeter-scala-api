package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.core.service.HolidayService
import com.advantest.demeter.utils.http.{ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
case class HolidayRoute() extends HttpRoute with ApiResponse {
  private val holidayService = HolidayService()

  override def route: Route = concat(
    getHolidaysRoute,
    getNationalHolidaysRoute,
    getCompanyHolidaysRoute,
    getSpecialWorkdaysRoute
  )

  private def getHolidaysRoute: Route = path("getHolidaysRoute") {
    get {
      val future = holidayService.getHolidays
      response(future)
    }
  }

  private def getNationalHolidaysRoute: Route = path("getNationalHolidaysRoute") {
    get {
      val future = holidayService.getNationalHolidays
      response(future)
    }
  }

  private def getCompanyHolidaysRoute: Route = path("getCompanyHolidaysRoute") {
    get {
      val future = holidayService.getCompanyHolidays
      response(future)
    }
  }

  private def getSpecialWorkdaysRoute: Route = path("getSpecialWorkdaysRoute") {
    get {
      val future = holidayService.getSpecialWorkdays
      response(future)
    }
  }
}
