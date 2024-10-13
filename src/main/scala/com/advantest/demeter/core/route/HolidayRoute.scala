package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.core.entity.HolidayEntity
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
    createHolidayRoute,
    createHolidaysRoute,
    deleteHolidaysRoute(),
    deleteHolidayByIdRoute(),
    deleteHolidayByIdsRoute(),
    updateHolidayRoute(),
    updateHolidaysRoute(),
    getHolidaysRoute,
    getNationalHolidaysRoute,
    getCompanyHolidaysRoute,
    getSpecialWorkdaysRoute
  )

  private def createHolidayRoute: Route = path("createHolidayRoute") {
    post {
      entity(as[HolidayEntity]) {
        request =>
          val future = holidayService.createHoliday(request)
          response(future)
      }
    }
  }

  private def createHolidaysRoute: Route = path("createHolidaysRoute") {
    post {
      entity(as[Seq[HolidayEntity]]) {
        request =>
          val future = holidayService.createHolidays(request)
          response(future)
      }
    }
  }

  private def deleteHolidaysRoute(): Route = path("deleteHolidaysRoute") {
    delete {
      val future = holidayService.deleteHolidays()
      response(future)
    }
  }

  private def deleteHolidayByIdRoute(): Route = path("deleteHolidayByIdRoute") {
    delete {
      entity(as[HttpRequestParams]) {
        request =>
          val holidayId = request.readLong("holidayId")
          val future = holidayService.deleteHolidayById(holidayId)
          response(future)
      }
    }
  }

  private def deleteHolidayByIdsRoute(): Route = path("deleteHolidayByIdsRoute") {
    delete {
      entity(as[HttpRequestParams]) {
        request =>
          val holidayIds = request.readLongArray("holidayIds")
          val future = holidayService.deleteHolidayByIds(holidayIds)
          response(future)
      }
    }
  }

  private def updateHolidayRoute(): Route = path("updateHolidayRoute") {
    put {
      entity(as[HolidayEntity]) {
        request =>
          val future = holidayService.updateHoliday(request)
          response(future)
      }
    }
  }

  private def updateHolidaysRoute(): Route = path("updateHolidaysRoute") {
    put {
      entity(as[Seq[HolidayEntity]]) {
        request =>
          val future = holidayService.updateHolidays(request)
          response(future)
      }
    }
  }

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
