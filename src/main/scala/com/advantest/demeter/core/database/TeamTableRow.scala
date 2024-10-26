package com.advantest.demeter.core.database

import com.advantest.demeter.utils.database.DBTableRow

import java.time.LocalDateTime

/**
 * Create on 2024/10/26
 * Author: mengen.dai@outlook.com
 */
final case class TeamTableRow(
                               id: Long,
                               name: String,
                               description: Option[String],
                               departmentId: Option[Long],
                               creatorId: Long,
                               updaterId: Long,
                               createDateTime: LocalDateTime = LocalDateTime.now(),
                               updateDateTime: LocalDateTime = LocalDateTime.now()
                             ) extends DBTableRow
