package com.advantest.demeter.constant.project

import com.advantest.demeter.database.DBTableColumn
import com.advantest.demeter.json.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/23
 * Author: mengen.dai@outlook.com
 */

sealed trait ProjectStatus

/**
 * NotStarted status, indicating that the project has not yet begun.
 */
case object NotStarted extends ProjectStatus

/**
 * RequirementsAnalysis status, indicating that the project is in the phase of analyzing requirements.
 */
case object RequirementsAnalysis extends ProjectStatus

/**
 * Planning status, indicating that the project is in the planning phase.
 */
case object Planning extends ProjectStatus

/**
 * InProgress status, indicating that the project is currently being worked on.
 */
case object InProgress extends ProjectStatus

/**
 * Acceptance status, indicating that the project is ready for acceptance or final review.
 */
case object Acceptance extends ProjectStatus

/**
 * Completed status, indicating that the project has been successfully completed.
 */
case object Completed extends ProjectStatus

/**
 * Cancelled status, indicating that the project has been canceled and will not be completed.
 */
case object Canceled extends ProjectStatus

/**
 * OnHold status, indicating that the project is temporarily paused or delayed.
 */
case object OnHold extends ProjectStatus

/**
 * Delayed status, indicating that the project is behind schedule and has been delayed.
 */
case object Delayed extends ProjectStatus

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
    case Canceled => 7
    case OnHold => 8
    case Delayed => 9
  }

  override def fromField(field: Int): ProjectStatus = field match {
    case 1 => NotStarted
    case 2 => RequirementsAnalysis
    case 3 => Planning
    case 4 => InProgress
    case 5 => Acceptance
    case 6 => Completed
    case 7 => Canceled
    case 8 => OnHold
    case 9 => Delayed
    case _ => throw new IllegalArgumentException(s"Invalid ProjectStatus field: $field. Valid fields are 1 (NotStarted), 2 (RequirementsAnalysis), 3 (Planning), 4 (InProgress), 5 (Acceptance), 6 (Completed), 7 (Cancelled), 8 (OnHold), 9 (Delayed).")
  }

  override implicit def columnMapper: BaseColumnType[ProjectStatus] = MappedColumnType.base[ProjectStatus, Int](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[ProjectStatus] = new RootJsonFormat[ProjectStatus] {
    override def write(obj: ProjectStatus): JsValue = JsNumber(fromModel(obj))

    override def read(json: JsValue): ProjectStatus = json match {
      case JsNumber(value) => fromField(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for ProjectStatus, but received a different type of JsValue.")
    }
  }

  val values: Seq[ProjectStatus] = Seq(NotStarted, RequirementsAnalysis, Planning, InProgress, Acceptance, Completed, Canceled, OnHold, Delayed)
}
