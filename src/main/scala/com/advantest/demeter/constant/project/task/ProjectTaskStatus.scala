package com.advantest.demeter.constant.project.task

import com.advantest.demeter.json.serialize.Serializable
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */

sealed trait ProjectTaskStatus {
  def toInt: Int
}

/**
 * NotStarted status, indicating that the task has not yet been started.
 */
case object NotStarted extends ProjectTaskStatus {
  override def toInt: Int = 1
}


/**
 * InProgress status, indicating that the task is currently being worked on.
 */
case object InProgress extends ProjectTaskStatus {
  override def toInt: Int = 2
}

/**
 * Acceptance status, indicating that the task is ready for acceptance or review.
 */
case object Acceptance extends ProjectTaskStatus {
  override def toInt: Int = 3
}

/**
 * Completed status, indicating that the task has been completed.
 */
case object Completed extends ProjectTaskStatus {
  override def toInt: Int = 4
}

/**
 * OnHold status, indicating that the task is temporarily paused or delayed.
 */
case object OnHold extends ProjectTaskStatus {
  override def toInt: Int = 5
}

/**
 * Canceled status, indicating that the task has been canceled and will not be completed.
 */
case object Canceled extends ProjectTaskStatus {
  override def toInt: Int = 6
}

object ProjectTaskStatus extends Serializable[ProjectTaskStatus] {

  def fromInt(field: Int): ProjectTaskStatus = field match {
    case 1 => NotStarted
    case 2 => InProgress
    case 3 => Acceptance
    case 4 => Completed
    case 5 => OnHold
    case 6 => Canceled
    case _ => throw new IllegalArgumentException(s"Invalid ProjectTaskStatus field: $field. Valid fields are 1 (NotStarted), 2 (InProgress), 3 (Acceptance), 4 (Completed), 5 (OnHold), 6 (Canceled).")
  }

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskStatus] = new RootJsonFormat[ProjectTaskStatus] {
    override def write(obj: ProjectTaskStatus): JsValue = JsNumber(obj.toInt)

    override def read(json: JsValue): ProjectTaskStatus = json match {
      case JsNumber(value) => fromInt(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for ProjectTaskStatus, but received a different type of JsValue.")
    }
  }

  val values: Seq[ProjectTaskStatus] = Seq(NotStarted, InProgress, Acceptance, Completed, OnHold, Canceled)
}