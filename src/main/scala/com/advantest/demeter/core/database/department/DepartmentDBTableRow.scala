package com.advantest.demeter.core.database.department

import com.advantest.demeter.core.entity.DepartmentEntity
import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class DepartmentDBTableRow(
                                     id: Long,
                                     name: String,
                                     description: Option[String],
                                     creatorId: Long,
                                     updaterId: Long,
                                     createDateTime: LocalDateTime = LocalDateTime.now(),
                                     updateDateTime: LocalDateTime = LocalDateTime.now()
                                   ) extends DBTableRow {
  override def toString: String = s"DepartmentTableRow(id=$id, name=$name, description=$description, creatorId=$creatorId, updaterId=$updaterId, createDateTime=$createDateTime, updateDateTime=$updateDateTime)"

  def toEntity: DepartmentEntity = DepartmentEntity(
    id = id,
    name = name,
    description = description
  )
}
