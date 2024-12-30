package com.advantest.demeter.database

import java.time.LocalDateTime

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * Trait representing a table row with basic metadata fields.
 * This trait is used to encapsulate common properties for table rows in a database.
 */
trait DBTableRow {

  /**
   * Unique identifier of the record.
   *
   * @return The ID of the record as a [[Long]].
   */
  val id: Long

  /**
   * Identifier of the user who created the record.
   *
   * @return The creator's ID as a [[Long]].
   */
  val creatorId: Long

  /**
   * Identifier of the user who last updated the record.
   *
   * @return The updater's ID as a [[Long]].
   */
  val updaterId: Long

  /**
   * Timestamp when the record was created.
   *
   * @return The creation time as a [[LocalDateTime]].
   */
  val createDateTime: LocalDateTime

  /**
   * Timestamp when the record was last updated.
   *
   * @return The last update time as a [[LocalDateTime]].
   */
  val updateDateTime: LocalDateTime
}

