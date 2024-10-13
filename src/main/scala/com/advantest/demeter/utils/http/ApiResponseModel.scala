package com.advantest.demeter.utils.http

import com.advantest.demeter.utils.serialize.format.writer.JsonWriterFormat
import spray.json.DefaultJsonProtocol._
import spray.json._

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 * Represents information about an API response, including its status, HTTP response code, and an optional error message.
 *
 * @param status Represents the status of the API response, which can be Success or Failure.
 * @param code   The HTTP response code, typically used to indicate the processing status of the request.
 * @param data   The HTTP response data.
 * @param error  An optional error message; it contains an error message if the API response status is Failure, otherwise it's None.
 */
case class ApiResponseModel[T](status: ApiStatus, code: HttpStatusCode, data: Option[T], error: Option[String])

object ApiResponseModel {

  /**
   * Implicitly defines a `RootJsonFormat` for `ApiResponseModel[T]`.
   *
   * This method creates a `RootJsonFormat` for serializing and deserializing `ApiResponseModel[T]` instances.
   * It supports writing `ApiResponseModel[T]` instances to JSON but does not support reading.
   *
   * @tparam T The type of the data contained in the `ApiResponseModel`.
   * @return A `RootJsonFormat` for `ApiResponseModel[T]`.
   */
  implicit def jsonFormat[T: JsonFormat]: RootJsonFormat[ApiResponseModel[T]] = new RootJsonFormat[ApiResponseModel[T]] {
    override def write(obj: ApiResponseModel[T]): JsValue = {
      val writer = JsonWriterFormat()
      val status = obj.status match {
        case ApiResponseFailure => "Failure"
        case ApiResponseSuccess => "Success"
        case _ => "None"
      }
      val fields = writer
        .writeString("status", status)
        .writeInt("code", HttpStatusCode.toCode(obj.code))
        .writeString("error", obj.error)
        .toJsValueMap
      JsObject(fields + ("data" -> obj.data.toJson))
    }

    override def read(json: JsValue): ApiResponseModel[T] = throw new UnsupportedOperationException("This method is not supported.")
  }


  /**
   * Creates an API response representing success.
   *
   * @return An API response with a success status, an HTTP response code of 200, and no error message.
   */
  def success(): JsValue = ApiResponseModel[Unit](ApiResponseSuccess, OK, None, None).toJson

  /**
   * Creates an API response representing success.
   *
   * @return An API response with a success status, an HTTP response code of 200, a data type of T, and no error message.
   */
  def success[T: JsonFormat](data: T): JsValue = ApiResponseModel[T](ApiResponseSuccess, OK, Some(data), None).toJson

  /**
   * Creates an API response representing failure with a specified error message.
   *
   * @param error The error message.
   * @return An API response with a failure status, a custom HTTP response code (-1), and the provided error message.
   */
  def failure(error: String): JsValue = ApiResponseModel[Int](ApiResponseFailure, INTERNAL_SERVER_ERROR, None, Some(error)).toJson
}