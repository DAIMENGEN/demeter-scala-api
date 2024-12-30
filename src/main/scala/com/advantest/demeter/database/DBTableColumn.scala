package com.advantest.demeter.database

import slick.jdbc.MySQLProfile.api._

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 * Trait representing a database table column.
 *
 * This trait defines the basic operations for converting between model types and field types,
 * as well as providing a column mapper for database interactions.
 *
 * ModelType: The type of the model used in the code.
 * FieldType: The type of the field used in the database.
 */
trait DBTableColumn {
  type ModelType
  type FieldType

  /**
   * Converts a model value to a field value.
   *
   * @param model The model value to convert.
   * @return The corresponding field value.
   */
  def fromModel(model: ModelType): FieldType

  /**
   * Converts a field value to a model value.
   *
   * @param field The field value to convert.
   * @return The corresponding model value.
   */
  def fromField(field: FieldType): ModelType

  /**
   * Provides an implicit column mapper for database interactions.
   *
   * @return An instance of `BaseColumnType` for the given model type.
   */
  implicit def columnMapper: BaseColumnType[ModelType]
}
