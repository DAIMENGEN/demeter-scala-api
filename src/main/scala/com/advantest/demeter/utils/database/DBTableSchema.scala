package com.advantest.demeter.utils.database

import slick.lifted.Rep

import java.time.LocalDateTime

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * This trait provides a blueprint for the structure of a database table, including the fields and their types.
 * Implementations of this trait should define the schema of a specific database table, including the fields and their types.
 */
trait DBTableSchema {

  /**
   * Unique identifier of the record.
   * @return A [[Rep[Long]]] representing the ID of the record.
   */
  def id: Rep[Long]

  /**
   * Identifier of the user who created the record.
   * @return A [[Rep[Long]]] representing the creator's ID.
   */
  def creatorId: Rep[Long]

  /**
   * Identifier of the user who last updated the record.
   * @return A [[Rep[Long]]] representing the updater's ID.
   */
  def updaterId: Rep[Long]

  /**
   * Timestamp when the record was created.
   * @return A [[Rep[LocalDateTime]]] representing the creation time.
   */
  def createDateTime: Rep[LocalDateTime]

  /**
   * Timestamp when the record was last updated.
   * @return A [[Rep[LocalDateTime]]] representing the last update time.
   */
  def updateDateTime(): Rep[LocalDateTime]
}
