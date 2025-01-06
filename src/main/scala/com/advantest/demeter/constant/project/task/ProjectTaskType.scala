package com.advantest.demeter.constant.project.task

import com.advantest.demeter.json.serialize.Serializable
import spray.json.{JsNumber, JsValue, RootJsonFormat}

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
sealed trait ProjectTaskType {
  def toInt: Int
}

/**
 * Milestone task type, representing important milestones or phases in the project.
 */
case object Milestone extends ProjectTaskType {
  def toInt: Int = 1
}

/**
 * Checkpoint task type, representing key checkpoints in the project.
 */
case object Checkpoint extends ProjectTaskType {
  def toInt: Int = 2
}

/**
 * Routine task type, representing regular project tasks.
 */
case object Routine extends ProjectTaskType {
  def toInt: Int = 3
}

/**
 * Bug task type, representing errors or defects that need to be fixed.
 */
case object Bug extends ProjectTaskType {
  def toInt: Int = 4
}

/**
 * Feature task type, representing new features that need to be developed.
 */
case object Feature extends ProjectTaskType {
  def toInt: Int = 5
}

/**
 * Enhancement task type, representing improvements or optimizations to existing features.
 */
case object Enhancement extends ProjectTaskType {
  def toInt: Int = 6
}

/**
 * Documentation task type, representing documents that need to be written or updated.
 */
case object Documentation extends ProjectTaskType {
  def toInt: Int = 7
}

/**
 * Research task type, representing research or investigations that need to be conducted.
 */
case object Research extends ProjectTaskType {
  def toInt: Int = 8
}

/**
 * Review task type, representing code reviews or other forms of review that need to be performed.
 */
case object Review extends ProjectTaskType {
  def toInt: Int = 9
}

object ProjectTaskType extends Serializable[ProjectTaskType] {

  def fromInt(field: Int): ProjectTaskType = field match {
    case 1 => Milestone
    case 2 => Checkpoint
    case 3 => Routine
    case 4 => Bug
    case 5 => Feature
    case 6 => Enhancement
    case 7 => Documentation
    case 8 => Research
    case 9 => Review
    case _ => throw new IllegalArgumentException(s"Invalid ProjectTaskType field: $field. Valid fields are 1 (Milestone), 2 (Checkpoint), 3 (Routine), 4 (Bug), 5 (Feature), 6 (Enhancement), 7 (Documentation), 8 (Research), 9 (Review).")
  }

  override implicit val serializeFormat: RootJsonFormat[ProjectTaskType] = new RootJsonFormat[ProjectTaskType] {
    override def write(obj: ProjectTaskType): JsValue = JsNumber(obj.toInt)

    override def read(json: JsValue): ProjectTaskType = json match {
      case JsNumber(value) => fromInt(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for ProjectTaskType, but received a different type of JsValue.")
    }
  }

  val values: Seq[ProjectTaskType] = Seq(Milestone, Checkpoint, Routine, Bug, Feature, Enhancement, Documentation, Research, Review)
}
