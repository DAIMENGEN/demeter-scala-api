package com.advantest.demeter.integration.antdesign.select

import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.{JsNumber, JsString, JsValue, RootJsonFormat}

/**
 * Create on 2024/11/9
 * Author: mengen.dai@outlook.com
 */
sealed trait Value
final case class IntValue(value: Int) extends Value
final case class LongValue(value: Long) extends Value

object Value extends Serializable[Value]{

  override implicit val serializeFormat: RootJsonFormat[Value] = new RootJsonFormat[Value] {
    override def write(obj: Value): JsValue = obj match {
      case IntValue(value: Int) => JsNumber(value)
      case LongValue(value: Long) => JsString(value.toString)
    }

    override def read(json: JsValue): Value = throw new UnsupportedOperationException("Reading Value from JSON is not supported.")
  }
}

final case class SelectOption(label: String, value: Value) {
  override def toString: String = s"SelectOption(label=$label, value=$value)"
}

object SelectOption extends Serializable[SelectOption] {
  override implicit val serializeFormat: RootJsonFormat[SelectOption] = jsonFormat2(SelectOption.apply)
}


