package com.advantest.demeter.utils.database

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 */
trait DBTableRowFactory {

  // Defines a type alias for entity data.
  protected type EntityData

  // Defines a type alias for row data, which must be a subclass of DBTableRow.
  protected type TableRowData <: DBTableRow

  // Defines a type alias for optional field data, which is a map of field names to any type.
  protected type OptionalData = Option[Map[String, Any]]

  /**
   * Creates a new row data object.
   *
   * @param employeeId The user ID associated with the row data.
   * @param entityData The entity data used to create the row data.
   * @return A new instance of `TableRowData`.
   */
  def create(employeeId: Long, entityData: EntityData, options: OptionalData = None): TableRowData

  /**
   * Updates an existing row data object.
   *
   * @param employeeId The user ID associated with the row data.
   * @param entityData The entity data used to update the row data.
   * @param oldRowData The existing row data object to be updated.
   * @return An updated instance of `TableRowData`.
   */
  def update(employeeId: Long, entityData: EntityData, oldRowData: TableRowData, options: OptionalData = None): TableRowData
}
