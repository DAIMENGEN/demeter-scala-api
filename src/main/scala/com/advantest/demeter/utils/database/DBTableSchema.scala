package com.advantest.demeter.utils.database

import slick.jdbc.MySQLProfile.api._

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


/**
 * A base class for defining the schema of a database table.
 * This class implements the [[DBTableSchema]] trait and provides the basic structure for a table,
 * including the fields and their types, as well as default behaviors for handling common columns like
 * `id`, `creatorId`, `updaterId`, `createDateTime`, and `updateDateTime`.
 *
 * @param tag The tag used to associate this table with the database schema.
 * @param tableName The name of the table in the database.
 * @tparam T The type of the table, which corresponds to the data entity represented by the table.
 */
abstract class DBTableSchemaAbstract[T](tag: Tag, tableName: String) extends Table[T] (tag, tableName) with DBTableSchema {

  /**
   * The unique identifier of the record.
   * This field is marked as the primary key and should be unique for each record in the table.
   *
   * @return A [[Rep[Long]]] representing the ID of the record.
   */
  override def id: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.Unique)

  /**
   * The identifier of the user who created the record.
   * This field stores the ID of the user responsible for creating the record in the database.
   *
   * @return A [[Rep[Long]]] representing the creator's ID.
   */
  override def creatorId: Rep[Long] = column[Long]("CREATOR_ID")

  /**
   * The identifier of the user who last updated the record.
   * This field stores the ID of the user responsible for the most recent update to the record.
   *
   * @return A [[Rep[Long]]] representing the updater's ID.
   */
  override def updaterId: Rep[Long] = column[Long]("UPDATER_ID")

  /**
   * The timestamp when the record was created.
   * This field stores the creation timestamp of the record.
   * The default value is set to the current time when the record is created.
   *
   * @return A [[Rep[LocalDateTime]]] representing the creation time of the record.
   */
  override def createDateTime: Rep[LocalDateTime] = column[LocalDateTime]("CREATE_DATE_TIME", O.Default(LocalDateTime.now()))

  /**
   * The timestamp when the record was last updated.
   * This field stores the last update timestamp of the record.
   * The default value is set to the current time when the record is created.
   *
   * @return A [[Rep[LocalDateTime]]] representing the last update time of the record.
   */
  override def updateDateTime(): Rep[LocalDateTime] = column[LocalDateTime]("UPDATE_DATE_TIME", O.Default(LocalDateTime.now()))
}
