package com.advantest.demeter.http.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.http.{ApiRequest, ApiResponse, HttpRoute}
import com.advantest.demeter.http.payload.HolidayPayload
import com.advantest.demeter.service.HolidayService
import spray.json.DefaultJsonProtocol._

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
case class HolidayRoute() extends HttpRoute with ApiRequest with ApiResponse {
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
      validateToken { employee =>
        entity(as[HolidayPayload]) {
          request =>
            val future = holidayService.createHoliday(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def createHolidaysRoute: Route = path("createHolidaysRoute") {
    post {
      validateToken { employee =>
        entity(as[Seq[HolidayPayload]]) {
          request =>
            val future = holidayService.createHolidays(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def deleteHolidaysRoute(): Route = path("deleteHolidaysRoute") {
    delete {
      validateToken { employee =>
        val future = holidayService.deleteHolidays(employee.id)
        response(future)
      }
    }
  }

  private def deleteHolidayByIdRoute(): Route = path("deleteHolidayByIdRoute") {
    delete {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val holidayId = request.readLong("holidayId")
            val future = holidayService.deleteHolidayById(employee.id, holidayId)
            response(future)
        }
      }
    }
  }

  private def deleteHolidayByIdsRoute(): Route = path("deleteHolidayByIdsRoute") {
    delete {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val holidayIds = request.readLongArray("holidayIds")
            val future = holidayService.deleteHolidayByIds(employee.id, holidayIds)
            response(future)
        }
      }
    }
  }

  private def updateHolidayRoute(): Route = path("updateHolidayRoute") {
    post {
      validateToken { employee =>
        entity(as[HolidayPayload]) {
          request =>
            val future = holidayService.updateHoliday(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def updateHolidaysRoute(): Route = path("updateHolidaysRoute") {
    put {
      validateToken { employee =>
        entity(as[Seq[HolidayPayload]]) {
          request =>
            val future = holidayService.updateHolidays(employee.id, request)
            response(future)
        }
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
