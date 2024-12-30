package com.advantest.demeter.database

import com.advantest.demeter.DemeterScalaApi.{DEMETER_EXECUTION_CONTEXT, DEMETER_SYSTEM}
import slick.jdbc.MySQLProfile.api._

import java.time.LocalDateTime
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * Defines a trait for database tables, providing basic CRUD operations for database tables.
 */
trait DBTable {

  // Defines a type alias for row data, which must be a subclass of DBTableRow.
  protected type TableRowData <: DBTableRow

  // A database instance for executing database operations.
  protected val db: Database

  // A table query object, representing the database table that can be operated on.
  protected val table: TableQuery[_ <: Table[TableRowData] with DBTableSchema]

  /**
   * Creates the database table if it does not already exist.
   */
  protected def createTableIfNotExists(): Unit = {
    // Ensure the database instance is not null.
    require(db != null, "Database instance must not be null")

    // Run the table creation command and handle the result.
    db.run(table.schema.createIfNotExists).onComplete {
      case Success(_) =>
        // Log a success message if the table is created or already exists.
        DEMETER_SYSTEM.log.info(s"Table created successfully, schema: ${table.schema.createStatements}")
      case Failure(error) =>
        // Log an error message if the table creation fails.
        DEMETER_SYSTEM.log.error(error.toString)
    }
  }

  /**
   * Provides access to the underlying table query object.
   *
   * @return
   */
  def tableQuery: TableQuery[_ <: Table[TableRowData] with DBTableSchema] = table

  /**
   * Inserts a row of data into the database table.
   *
   * @param data The row data to be inserted, of type `TableRowData`.
   * @return A `Future` containing the inserted row data. If the insertion fails, a `RuntimeException` is thrown.
   */
  def insert(data: TableRowData): Future[TableRowData] = {
    val insert = table += data
    val select = table.filter(_.id === data.id).result.headOption
    val transaction = (insert andThen select).transactionally
    db.run(transaction).map {
      case Some(value) => value
      case None => throw new RuntimeException(s"Insertion failed for data with ID ${data.id}. Insert statement: ${insert.statements}, Select statement: ${select.statements}")
    }
  }

  /**
   * Performs a batch insert of multiple rows of data into the database table.
   *
   * @param dataList A sequence of row data to be inserted, each of type `TableRowData`.
   * @return A `Future` containing the inserted row data.
   */
  def batchInsert(dataList: Seq[TableRowData]): Future[Seq[TableRowData]] = {
    val ids = dataList.map(_.id)
    val insert = table ++= dataList
    val select = table.filter(_.id.inSet(ids)).result
    val transaction = (insert andThen select).transactionally
    db.run(transaction)
  }

  /**
   * Deletes all data from the database table.
   *
   * @return A `Future` containing the deleted row data.
   */
  def delete(): Future[Seq[TableRowData]] = {
    val select = table.result
    val delete = table.delete
    val transaction = (select zipWith delete)((r1, _) => r1).transactionally
    db.run(transaction)
  }

  /**
   * Deletes a row of data by its ID.
   *
   * @param id The ID of the row to be deleted.
   * @return A `Future` containing the deleted row data. If the row does not exist, a `RuntimeException` is thrown.
   */
  def deleteById(id: Long): Future[TableRowData] = {
    val select = table.filter(_.id === id).result.headOption
    val delete = table.filter(_.id === id).delete
    val transaction = (select zipWith delete)((option, _) => option match {
      case Some(value) => value
      case None => throw new RuntimeException(s"Attempted to delete non-existent data with ID $id. Select statement: ${select.statements}, Delete statement: ${delete.statements}")
    }).transactionally
    db.run(transaction)
  }

  /**
   * Deletes multiple rows of data by their IDs.
   *
   * @param ids A sequence of IDs of the rows to be deleted.
   * @return A `Future` containing the deleted row data.
   */
  def deleteByIds(ids: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.id.inSet(ids)).result
    val delete = table.filter(_.id.inSet(ids)).result
    val transaction = (select zipWith delete)((r1, _) => r1).transactionally
    db.run(transaction)
  }

  /**
   * Updates a row of data.
   *
   * @param data The updated row data, of type `TableRowData`.
   * @return A `Future` containing the updated row data. If the row does not exist, a `RuntimeException` is thrown.
   */
  def update(data: TableRowData): Future[TableRowData] = {
    val update = table.filter(_.id === data.id).update(data)
    val select = table.filter(_.id === data.id).result.headOption
    val transaction = (update andThen select).transactionally
    db.run(transaction).map {
      case Some(value) => value
      case None => throw new RuntimeException(s"Attempted to update non-existent data with ID ${data.id}. Update statement: ${update.statements}, Select statement: ${select.statements}")
    }
  }

  /**
   * Updates multiple rows of data.
   *
   * @param dataList A sequence of row data to be updated, each of type `TableRowData`.
   * @return A `Future` containing the updated row data.
   */
  def update(dataList: Seq[TableRowData]): Future[Seq[TableRowData]] = Future.sequence(dataList.map(update))

  /**
   * Queries all rows of data from the table.
   *
   * @return A `Future` containing a sequence of row data.
   */
  def query(): Future[Seq[TableRowData]] = db.run(table.result)

  /**
   * Queries a row of data by its ID.
   *
   * @param id The ID of the row to be queried.
   * @return A `Future` containing an optional row data.
   */
  def queryById(id: Long): Future[Option[TableRowData]] = {
    val select = table.filter(_.id === id).result.headOption
    db.run(select)
  }

  /**
   * Queries multiple rows of data by their IDs.
   *
   * @param ids A sequence of IDs of the rows to be queried.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByIds(ids: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.id.inSet(ids)).result
    db.run(select)
  }

  /**
   * Queries rows of data by the creator's ID.
   *
   * @param creatorId The ID of the creator.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByCreatorId(creatorId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.creatorId === creatorId).result
    db.run(select)
  }

  /**
   * Queries rows of data by the creators' IDs.
   *
   * @param creatorIds A sequence of IDs of the creators.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByCreatorIds(creatorIds: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.creatorId.inSet(creatorIds)).result
    db.run(select)
  }

  /**
   * Queries rows of data by the updater's ID.
   *
   * @param updaterId The ID of the updater.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByUpdaterId(updaterId: Long): Future[Seq[TableRowData]] = {
    val select = table.filter(_.updaterId === updaterId).result
    db.run(select)
  }

  /**
   * Queries rows of data by the updaters' IDs.
   *
   * @param updaterIds A sequence of IDs of the updaters.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByUpdaterIds(updaterIds: Seq[Long]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.updaterId.inSet(updaterIds)).result
    db.run(select)
  }

  /**
   * Queries rows of data by the creation date and time.
   *
   * @param createDateTime The creation date and time.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByCreateDateTime(createDateTime: LocalDateTime): Future[Seq[TableRowData]] = {
    val select = table.filter(_.createDateTime === createDateTime).result
    db.run(select)
  }

  /**
   * Queries rows of data by the creation date and times.
   *
   * @param createDateTimes A sequence of creation date and times.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByCreateDateTimes(createDateTimes: Seq[LocalDateTime]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.createDateTime.inSet(createDateTimes)).result
    db.run(select)
  }

  /**
   * Queries rows of data by the update date and time.
   *
   * @param updateDateTime The update date and time.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByUpdateDateTime(updateDateTime: LocalDateTime): Future[Seq[TableRowData]] = {
    val select = table.filter(_.updateDateTime() === updateDateTime).result
    db.run(select)
  }

  /**
   * Queries rows of data by the update date and times.
   *
   * @param updateDateTimes A sequence of update date and times.
   * @return A `Future` containing a sequence of row data.
   */
  def queryByUpdateDateTimes(updateDateTimes: Seq[LocalDateTime]): Future[Seq[TableRowData]] = {
    val select = table.filter(_.updateDateTime().inSet(updateDateTimes)).result
    db.run(select)
  }
}
