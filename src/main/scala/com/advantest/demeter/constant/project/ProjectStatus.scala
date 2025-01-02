package com.advantest.demeter.constant.project

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.DBTableColumn
import com.advantest.demeter.json.serialize.Serializable
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/23
 * Author: mengen.dai@outlook.com
 */

sealed trait ProjectStatus {
  def toInt: Int
}

/**
 * NotStarted status, indicating that the project has not yet begun.
 */
case object NotStarted extends ProjectStatus {
  override def toInt: Int = 1
}

/**
 * RequirementsAnalysis status, indicating that the project is in the phase of analyzing requirements.
 */
case object RequirementsAnalysis extends ProjectStatus {
  override def toInt: Int = 2
}

/**
 * Planning status, indicating that the project is in the planning phase.
 */
case object Planning extends ProjectStatus {
  override def toInt: Int = 3
}

/**
 * InProgress status, indicating that the project is currently being worked on.
 */
case object InProgress extends ProjectStatus {
  override def toInt: Int = 4
}

/**
 * Acceptance status, indicating that the project is ready for acceptance or final review.
 */
case object Acceptance extends ProjectStatus {
  override def toInt: Int = 5
}

/**
 * Completed status, indicating that the project has been successfully completed.
 */
case object Completed extends ProjectStatus {
  override def toInt: Int = 6
}

/**
 * Cancelled status, indicating that the project has been canceled and will not be completed.
 */
case object Canceled extends ProjectStatus {
  override def toInt: Int = 7
}

/**
 * OnHold status, indicating that the project is temporarily paused or delayed.
 */
case object OnHold extends ProjectStatus {
  override def toInt: Int = 8
}

/**
 * Delayed status, indicating that the project is behind schedule and has been delayed.
 */
case object Delayed extends ProjectStatus {
  override def toInt: Int = 9
}

object ProjectStatus extends Serializable[ProjectStatus] {

  def fromInt(field: Int): ProjectStatus = field match {
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

  override implicit val serializeFormat: RootJsonFormat[ProjectStatus] = new RootJsonFormat[ProjectStatus] {
    override def write(obj: ProjectStatus): JsValue = JsNumber(obj.toInt)

    override def read(json: JsValue): ProjectStatus = json match {
      case JsNumber(value) => fromInt(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for ProjectStatus, but received a different type of JsValue.")
    }
  }

  val values: Seq[ProjectStatus] = Seq(NotStarted, RequirementsAnalysis, Planning, InProgress, Acceptance, Completed, Canceled, OnHold, Delayed)
}
