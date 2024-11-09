package com.advantest.demeter.integration.antdesign.select

import com.advantest.demeter.utils.serialize.Serializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

/**
 * Create on 2024/11/9
 * Author: mengen.dai@outlook.com
 */
final case class SelectOption(label: String, value: Int) {
  override def toString: String = s"SelectOption(label=$label, value=$value)"
}

object SelectOption extends Serializable[SelectOption] {
  override implicit val serializeFormat: RootJsonFormat[SelectOption] = jsonFormat2(SelectOption.apply)
}


