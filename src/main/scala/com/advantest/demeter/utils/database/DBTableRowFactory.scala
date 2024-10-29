package com.advantest.demeter.utils.database

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 *
 * The `DBTableRowFactory` trait defines a factory interface for creating and updating
 * database table row data objects.
 *
 * It provides type aliases for entity data and row data, ensuring that any implementations
 * adhere to specific data structures. The trait also includes a type alias for optional
 * field data, represented as an `Option` containing a map of field names to values of any type.
 *
 * Key Methods:
 * - `create`: Creates a new instance of `TableRowData` using the provided employee ID and entity data.
 * - `update`: Updates an existing `TableRowData` instance with new entity data and returns the updated instance.
 *
 * Implementations of this trait should define how to handle the creation and updating of
 * database row data specific to their application context.
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
