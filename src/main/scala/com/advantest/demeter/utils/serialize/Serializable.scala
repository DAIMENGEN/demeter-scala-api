package com.advantest.demeter.utils.serialize

import spray.json.RootJsonFormat

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait Serializable[T] extends DefaultSerializeProtocol {
  implicit val serializeFormat: RootJsonFormat[T]
}
