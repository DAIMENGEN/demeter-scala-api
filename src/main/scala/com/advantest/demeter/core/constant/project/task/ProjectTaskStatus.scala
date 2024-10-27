package com.advantest.demeter.core.constant.project.task

import com.advantest.demeter.utils.database.DBTableColumn
import com.advantest.demeter.utils.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */

sealed trait ProjectTaskStatus

/**
 * NotStarted status, indicating that the task has not yet been started.
 */
case object NotStarted extends ProjectTaskStatus

/**
 * InProgress status, indicating that the task is currently being worked on.
 */
case object InProgress extends ProjectTaskStatus

/**
 * Acceptance status, indicating that the task is ready for acceptance or review.
 */
case object Acceptance extends ProjectTaskStatus

/**
 * Completed status, indicating that the task has been completed.
 */
case object Completed extends ProjectTaskStatus

/**
 * OnHold status, indicating that the task is temporarily paused or delayed.
 */
case object OnHold extends ProjectTaskStatus

/**
 * Canceled status, indicating that the task has been canceled and will not be completed.
 */
case object Canceled extends ProjectTaskStatus

object ProjectTaskStatus extends DBTableColumn with Serializable[ProjectTaskStatus] {

  override type ModelType = ProjectTaskStatus
  override type FieldType = Int

  override def fromModel(model: ProjectTaskStatus): Int = model match {
    case NotStarted => 1
    case InProgress => 2
    case Acceptance => 3
    case Completed => 4
    case OnHold => 5
    case Canceled => 6
  }

  override def fromField(field: Int): ProjectTaskStatus = field match {
    case 1 => NotStarted
    case 2 => InProgress
    case 3 => Acceptance
    case 4 => Completed
    case 5 => OnHold
    case 6 => Canceled
    case _ => throw new IllegalArgumentException(s"Invalid ProjectTaskStatus field: $field. Valid fields are 1 (NotStarted), 2 (InProgress), 3 (Acceptance), 4 (Completed), 5 (OnHold), 6 (Canceled).")
  }

  override implicit def columnMapper: BaseColumnType[ProjectTaskStatus] = MappedColumnType.base[ProjectTaskStatus, Int](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskStatus] = new RootJsonFormat[ProjectTaskStatus] {
    override def write(obj: ProjectTaskStatus): JsValue = JsNumber(fromModel(obj))

    override def read(json: JsValue): ProjectTaskStatus = json match {
      case JsNumber(value) => fromField(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for ProjectTaskStatus, but received a different type of JsValue.")
    }
  }
}