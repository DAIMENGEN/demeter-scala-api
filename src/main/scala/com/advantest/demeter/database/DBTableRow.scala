package com.advantest.demeter.database

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
   * @return The ID of the record as a [[DBLongValue]].
   */
  val id: DBLongValue

  /**
   * Identifier of the user who created the record.
   *
   * @return The creator's ID as a [[DBLongValue]].
   */
  val creatorId: DBLongValue

  /**
   * Identifier of the user who last updated the record.
   *
   * @return The updater's ID as a [[DBLongValue]].
   */
  val updaterId: DBLongValue

  /**
   * Timestamp when the record was created.
   *
   * @return The creation time as a [[DBDateTimeValue]].
   */
  val createDateTime: DBDateTimeValue

  /**
   * Timestamp when the record was last updated.
   *
   * @return The last update time as a [[DBDateTimeValue]].
   */
  val updateDateTime: DBDateTimeValue
}

