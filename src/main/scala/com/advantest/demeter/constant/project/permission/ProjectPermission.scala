package com.advantest.demeter.constant.project.permission

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.database.DBTableColumn
import com.advantest.demeter.json.serialize.Serializable
import spray.json.{JsNumber, JsObject, JsString, JsValue, RootJsonFormat, enrichAny}

/**
 * Create on 2024/10/30
 * Author: mengen.dai@outlook.com
 */
sealed trait ProjectPermission {
  type PermissionCategory = ProjectPermissionCategory
  val id: Int
  val name: String
  val order: Int
  val category: PermissionCategory

  override def equals(obj: Any): Boolean = obj match {
    case permission: ProjectPermission => id == permission.id && category == permission.category
    case _ => false
  }
}

case object ProjAdminPerm extends ProjectPermission {
  override val id: Int = 1
  override val name: String = "Admin"
  override val order: Int = 1
  override val category: PermissionCategory = ProjOverallPermCat
}

case object ProjViewPerm extends ProjectPermission {
  override val id: Int = 2
  override val name: String = "Read"
  override val order: Int = 2
  override val category: PermissionCategory = ProjOverallPermCat
}

case object ProjTaskCreatePerm extends ProjectPermission {
  override val id: Int = 100
  override val name: String = "Create"
  override val order: Int = 3
  override val category: PermissionCategory = ProjTaskPermCat
}

case object ProjTaskEditPerm extends ProjectPermission {
  override val id: Int = 101
  override val name: String = "Edit"
  override val order: Int = 4
  override val category: PermissionCategory = ProjTaskPermCat
}

case object ProjTaskDeletePerm extends ProjectPermission {
  override val id: Int = 102
  override val name: String = "Delete"
  override val order: Int = 5
  override val category: PermissionCategory = ProjTaskPermCat
}

case object ProjTaskViewPerm extends ProjectPermission {
  override val id: Int = 103
  override val name: String = "Read"
  override val order: Int = 6
  override val category: PermissionCategory = ProjTaskPermCat
}

case object ProHistoryCreatePerm extends ProjectPermission {
  override val id: Int = 200
  override val name: String = "Create"
  override val order: Int = 8
  override val category: PermissionCategory = ProjHistoryPermCat
}

case object ProjHistoryViewPerm extends ProjectPermission {
  override val id: Int = 201
  override val name: String = "Read"
  override val order: Int = 7
  override val category: PermissionCategory = ProjHistoryPermCat
}

case object ProjHistoryDeletePerm extends ProjectPermission {
  override val id: Int = 202
  override val name: String = "Delete"
  override val order: Int = 9
  override val category: PermissionCategory = ProjHistoryPermCat
}

case object ProjDocViewPerm extends ProjectPermission {
  override val id: Int = 300
  override val name: String = "Read"
  override val order: Int = 8
  override val category: PermissionCategory = ProjDocPermCat
}

case object ProjDocUploadPerm extends ProjectPermission {
  override val id: Int = 301
  override val name: String = "Upload"
  override val order: Int = 10
  override val category: PermissionCategory = ProjDocPermCat
}

case object ProjDocDeletePerm extends ProjectPermission {
  override val id: Int = 302
  override val name: String = "Delete"
  override val order: Int = 11
  override val category: PermissionCategory = ProjDocPermCat
}

object ProjectPermission extends DBTableColumn with Serializable[ProjectPermission] {
  override type ModelType = ProjectPermission
  override type FieldType = Int

  override def fromModel(model: ProjectPermission): Int = model.id

  override def fromField(field: Int): ProjectPermission = field match {
    case 1 => ProjAdminPerm
    case 2 => ProjViewPerm
    case 100 => ProjTaskCreatePerm
    case 101 => ProjTaskEditPerm
    case 102 => ProjTaskDeletePerm
    case 103 => ProjTaskViewPerm
    case 200 => ProHistoryCreatePerm
    case 201 => ProjHistoryViewPerm
    case 202 => ProjHistoryDeletePerm
    case 300 => ProjDocViewPerm
    case 301 => ProjDocUploadPerm
    case 302 => ProjDocDeletePerm
  }

  override implicit def columnMapper: BaseColumnType[ProjectPermission] = MappedColumnType.base[ProjectPermission, Int](fromModel, fromField)

  override implicit val serializeFormat: RootJsonFormat[ProjectPermission] = new RootJsonFormat[ProjectPermission] {
    override def write(obj: ProjectPermission): JsValue = JsObject(
      "id" -> JsNumber(obj.id),
      "name" -> JsString(obj.name),
      "order" -> JsNumber(obj.order),
      "category" -> obj.category.toJson
    )

    override def read(json: JsValue): ProjectPermission = json match {
      case JsNumber(value) => fromField(value.toInt)
      case _ => throw new IllegalArgumentException()
    }
  }
}