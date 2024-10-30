package com.advantest.demeter.core.constant.project.permission

import com.advantest.demeter.utils.database.DBTableColumn
import com.advantest.demeter.utils.serialize.Serializable
import slick.jdbc.MySQLProfile.api._
import spray.json.{JsNumber, JsObject, JsString, JsValue, RootJsonFormat}
/**
 * Create on 2024/10/30
 * Author: mengen.dai@outlook.com
 */
sealed trait ProjectPermissionCategory {
  val id: Int
  val name: String

  override def equals(obj: Any): Boolean = obj match {
    case category: ProjectPermissionCategory => id == category.id
    case _ => false
  }
}

case object ProjOverallPermCat extends ProjectPermissionCategory {
  override val id: Int = 1
  override val name: String = "Overall"
}

case object ProjTaskPermCat extends ProjectPermissionCategory {
  override val id: Int = 2
  override val name: String = "Task"
}

case object ProjHistoryPermCat extends ProjectPermissionCategory {
  override val id: Int = 3
  override val name: String = "History"
}

case object ProjDocPermCat extends ProjectPermissionCategory {
  override val id: Int = 4
  override val name: String = "Document"
}

object ProjectPermissionCategory extends DBTableColumn with Serializable[ProjectPermissionCategory] {
  override type ModelType = ProjectPermissionCategory
  override type FieldType = Int

  override def fromModel(model: ProjectPermissionCategory): Int = model match {
    case ProjOverallPermCat => 1
    case ProjTaskPermCat => 2
    case ProjHistoryPermCat => 3
    case ProjDocPermCat => 4
  }

  override def fromField(field: Int): ProjectPermissionCategory = field match {
    case 1 => ProjOverallPermCat
    case 2 => ProjTaskPermCat
    case 3 => ProjHistoryPermCat
    case 4 => ProjDocPermCat
    case _ => throw new IllegalArgumentException(s"Invalid ProjectPermissionCategory field: $field. Valid fields are 1 (ProjOverallPermCat), 2 (ProjTaskPermCat), 3 (ProjHistoryPermCat), 4 (ProjDocPermCat).")
  }

  override implicit def columnMapper: BaseColumnType[ProjectPermissionCategory] = MappedColumnType.base[ProjectPermissionCategory, Int](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[ProjectPermissionCategory] = new RootJsonFormat[ProjectPermissionCategory] {
    override def write(obj: ProjectPermissionCategory): JsValue = JsObject(
      "id" -> JsNumber(obj.id),
      "name" -> JsString(obj.name)
    )

    override def read(json: JsValue): ProjectPermissionCategory = json match {
      case JsNumber(value) => fromField(value.toInt)
      case _ => throw new IllegalArgumentException("Expected a JsNumber for ProjectPermissionCategory, but received a different type of JsValue.")
    }
  }
}