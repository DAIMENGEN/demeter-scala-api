package com.advantest.demeter.utils.database

import com.advantest.demeter.utils.http.HttpPayload

/**
 * Create on 2024/10/24
 * Author: mengen.dai@outlook.com
 */
trait DBTableRowFactory {

  // The type of data that will be used as the payload for creating or updating a database table row.
  // This data is typically extracted from an HTTP request or response.
  protected type PayloadData <: HttpPayload

  // The type representing a database table row. Must be a subtype of DBTableRow.
  protected type DBTableRowData <: DBTableRow

  // The type representing optional additional data, encapsulated as an Option of a Map with String keys and Any values.
  protected type OptionalData = Option[Map[String, Any]]

  /**
   * Creates a new database table row based on the provided employee ID, payload data,
   * and optional additional data.
   *
   * @param employeeId The unique identifier of the employee.
   * @param payloadData The payload data extracted from an HTTP request or response, used to construct the database table row.
   * @param options Additional optional data, defaulting to None.
   * @return An instance of DBTableRowData representing the newly created row.
   */
  def create(employeeId: Long, payloadData: PayloadData, options: OptionalData = None): DBTableRowData

  /**
   * Updates an existing database table row using the provided employee ID, payload data,
   * old row data, and optional additional data.
   *
   * @param employeeId The unique identifier of the employee.
   * @param payloadData The payload data extracted from an HTTP request or response, used to update the database table row.
   * @param oldRowData The existing row data that will be updated.
   * @param options Additional optional data, defaulting to None.
   * @return An instance of DBTableRowData representing the updated row.
   */
  def update(employeeId: Long, payloadData: PayloadData, oldRowData: DBTableRowData, options: OptionalData = None): DBTableRowData
}
