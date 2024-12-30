package com.advantest.demeter.json.serialize

import spray.json.RootJsonFormat

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * Defines a trait for serializing objects of any type T.
 * This trait extends DefaultSerializeProtocol to inherit default serialization protocols.
 * The primary purpose is to provide an implicit serialization format that can convert objects of type T into JSON format.
 *
 * @tparam T The type of object to be serialized
 */
trait Serializable[T] extends DefaultSerializeProtocol {

  /**
   * Implicit value that defines the serialization format for type T.
   * This format is a root JSON format, meaning it can serialize and deserialize objects of type T.
   */
  implicit val serializeFormat: RootJsonFormat[T]
}

