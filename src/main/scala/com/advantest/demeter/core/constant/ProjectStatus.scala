package com.advantest.demeter.core.constant

import com.advantest.demeter.utils.database.DBTableColumn
import com.advantest.demeter.utils.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/23
 * Author: mengen.dai@outlook.com
 */

sealed trait ProjectStatus

case object NotStarted extends ProjectStatus

case object RequirementsAnalysis extends ProjectStatus

case object Planning extends ProjectStatus

case object InProgress extends ProjectStatus

case object Acceptance extends ProjectStatus

case object Completed extends ProjectStatus

case object Cancelled extends ProjectStatus

case object OnHold extends ProjectStatus

case object Delayed extends ProjectStatus

case object Testing extends ProjectStatus

object ProjectStatus extends DBTableColumn with Serializable[ProjectStatus] {

  override type ModelType = ProjectStatus
  override type FieldType = Int


  override def fromModel(model: ProjectStatus): Int = model match {
    case NotStarted => 1
    case RequirementsAnalysis => 2
    case Planning => 3
    case InProgress => 4
    case Acceptance => 5
    case Completed => 6
    case Cancelled => 7
    case OnHold => 8
    case Delayed => 9
    case Testing => 10
  }

  override def fromField(field: Int): ProjectStatus = field match {
    case 1 => NotStarted
    case 2 => RequirementsAnalysis
    case 3 => Planning
    case 4 => InProgress
    case 5 => Acceptance
    case 6 => Completed
    case 7 => Cancelled
    case 8 => OnHold
    case 9 => Delayed
    case 10 => Testing
    case _ => throw new IllegalArgumentException(s"Invalid ProjectStatus field: $field. Valid fields are 1 (NotStarted), 2 (RequirementsAnalysis), 3 (Planning), 4 (InProgress), 5 (Acceptance), 6 (Completed), 7 (Cancelled), 8 (OnHold), 9 (Delayed), 10 (Testing).")
  }
  
  override implicit def columnMapper: BaseColumnType[ProjectStatus] = MappedColumnType.base[ProjectStatus, Int](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[ProjectStatus] = new RootJsonFormat[ProjectStatus] {
    override def write(obj: ProjectStatus): JsValue = JsNumber(fromModel(obj))

    override def read(json: JsValue): ProjectStatus = json match {
      case JsNumber(value) => fromField(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for ProjectStatus, but received a different type of JsValue.")
    }
  }
}
