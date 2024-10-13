package com.advantest.demeter.utils.database

import com.advantest.demeter.DemeterScalaApi.{DEMETER_EXECUTION_CONTEXT, DEMETER_SYSTEM}
import slick.jdbc.MySQLProfile.api._

import java.time.LocalDateTime
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait DBTable {

  protected type TableData <: DBTableData

  protected type EntityData <: DBEntityData

  protected val db: Database

  protected val table: TableQuery[_ <: Table[EntityData] with TableData]

  protected def createTableIfNotExists(): Unit = {
    // If the current database table does not exist, create the database table.
    require(db != null, "Database instance must not be null")
    db.run(table.schema.createIfNotExists).onComplete {
      case Success(_) => DEMETER_SYSTEM.log.info(s"Table created successfully, schema: ${table.schema.createStatements}")
      case Failure(error) => DEMETER_SYSTEM.log.error(error.toString)
    }
  }

  def insert(data: EntityData): Future[EntityData] = {
    val insert = table += data
    val select = table.filter(_.id === data.id).result.headOption
    val transaction = (insert andThen select).transactionally
    db.run(transaction).map {
      case Some(value) => value
      case None => throw new RuntimeException(s"Insertion failed for data with ID ${data.id}. Insert statement: ${insert.statements}, Select statement: ${select.statements}")
    }
  }

  def batchInsert(dataList: Seq[EntityData]): Future[Seq[EntityData]] = {
    val ids = dataList.map(_.id)
    val insert = table ++= dataList
    val select = table.filter(_.id.inSet(ids)).result
    val transaction = (insert andThen select).transactionally
    db.run(transaction)
  }

  def delete(): Future[Seq[EntityData]] = {
    val select = table.result
    val delete = table.delete
    val transaction = (select zipWith delete)((r1, _) => r1).transactionally
    db.run(transaction)
  }

  def deleteById(id: Long): Future[EntityData] = {
    val select = table.filter(_.id === id).result.headOption
    val delete = table.filter(_.id === id).delete
    val transaction = (select zipWith delete)((option, _) => option match {
      case Some(value) => value
      case None => throw new RuntimeException(s"Attempted to delete non-existent data with ID $id. Select statement: ${select.statements}, Delete statement: ${delete.statements}")
    }).transactionally
    db.run(transaction)
  }

  def deleteByIds(ids: Seq[Long]): Future[Seq[EntityData]] = {
    val select = table.filter(_.id.inSet(ids)).result
    val delete = table.filter(_.id.inSet(ids)).result
    val transaction = (select zipWith delete)((r1, _) => r1).transactionally
    db.run(transaction)
  }

  def update(data: EntityData): Future[EntityData] = {
    val update = table.filter(_.id === data.id).update(data)
    val select = table.filter(_.id === data.id).result.headOption
    val transaction = (update andThen select).transactionally
    db.run(transaction).map {
      case Some(value) => value
      case None => throw new RuntimeException(s"Attempted to update non-existent data with ID ${data.id}. Update statement: ${update.statements}, Select statement: ${select.statements}")
    }
  }

  def update(dataList: Seq[EntityData]): Future[Seq[EntityData]] = Future.sequence(dataList.map(update))

  def query(): Future[Seq[EntityData]] = db.run(table.result)

  def queryById(id: Long): Future[Option[EntityData]] = {
    val select = table.filter(_.id === id).result.headOption
    db.run(select)
  }

  def queryByIds(ids: Seq[Long]): Future[Seq[EntityData]] = {
    val select = table.filter(_.id.inSet(ids)).result
    db.run(select)
  }

  def queryByCreatorId(creatorId: Long): Future[Seq[EntityData]] = {
    val select = table.filter(_.creatorId === creatorId).result
    db.run(select)
  }

  def queryByCreatorIds(creatorIds: Seq[Long]): Future[Seq[EntityData]] = {
    val select = table.filter(_.creatorId.inSet(creatorIds)).result
    db.run(select)
  }

  def queryByUpdaterId(updaterId: Long): Future[Seq[EntityData]] = {
    val select = table.filter(_.updaterId === updaterId).result
    db.run(select)
  }

  def queryByUpdaterIds(updaterIds: Seq[Long]): Future[Seq[EntityData]] = {
    val select = table.filter(_.updaterId.inSet(updaterIds)).result
    db.run(select)
  }

  def queryByCreateDateTime(createDateTime: LocalDateTime): Future[Seq[EntityData]] = {
    val select = table.filter(_.createDateTime === createDateTime).result
    db.run(select)
  }

  def queryByCreateDateTimes(createDateTimes: Seq[LocalDateTime]): Future[Seq[EntityData]] = {
    val select = table.filter(_.createDateTime.inSet(createDateTimes)).result
    db.run(select)
  }

  def queryByUpdateDateTime(updateDateTime: LocalDateTime): Future[Seq[EntityData]] = {
    val select = table.filter(_.updateDateTime === updateDateTime).result
    db.run(select)
  }

  def queryByUpdateDateTimes(updateDateTimes: Seq[LocalDateTime]): Future[Seq[EntityData]] = {
    val select = table.filter(_.updateDateTime.inSet(updateDateTimes)).result
    db.run(select)
  }

}
