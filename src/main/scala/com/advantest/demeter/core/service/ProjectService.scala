package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.constant.project.ProjectStatus
import com.advantest.demeter.core.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.core.database.project.color.ProjectColorDBTable
import com.advantest.demeter.core.database.project.task.ProjectTaskDBTable
import com.advantest.demeter.core.database.project.task.field.ProjectTaskFieldDBTable
import com.advantest.demeter.core.database.project.{ProjectDBTable, ProjectDBTableRow}
import com.advantest.demeter.core.entity.project.ProjectEntity
import com.advantest.demeter.integration.antdesign.select.{IntValue, SelectOption}

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
case class ProjectService() extends Service {
  private val table: ProjectDBTable = ProjectDBTable()
  private val taskTable: ProjectTaskDBTable = ProjectTaskDBTable()
  private val colorTable: ProjectColorDBTable = ProjectColorDBTable()
  private val taskFieldTable: ProjectTaskFieldDBTable = ProjectTaskFieldDBTable()

  private val employeeService: EmployeeService = EmployeeService()

  private def isCreator(employeeId: Long, projectId: Long): Future[Boolean] = {
    logger.info(s"Checking if employee $employeeId is the creator of project $projectId.")
    table.queryById(projectId).map {
      case Some(value) => value.creatorId == employeeId
      case None => throw new NoSuchElementException(s"Project with ID $projectId not found")
    }
  }

  def createProject(employeeId: Long, project: ProjectEntity): Future[ProjectEntity] = {
    val tableRowData = ProjectEntity.create(employeeId, project)
    table.insert(tableRowData).map(_.toEntity)
  }

  def createProjects(employeeId: Long, projects: Seq[ProjectEntity]): Future[Seq[ProjectEntity]] = {
    val tableRows = projects.map(project => ProjectEntity.create(employeeId, project))
    table.batchInsert(tableRows).map(_.map(_.toEntity))
  }

  def deleteProjects(employeeId: Long): Future[Seq[ProjectEntity]] = {
    if (!employeeService.checkIfAdmin(employeeId)) throw new SecurityException("Only system admin can delete projects.")
    table.delete().map(_.map(_.toEntity))
  }

  def deleteProjectById(employeeId: Long, id: Long): Future[ProjectEntity] = {
    logger.info(s"Employee with ID $employeeId is deleting project with ID $id")
    table.deleteById(id).map(_.toEntity)
  }

  def deleteProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectEntity]] = {
    logger.info(s"Employee with ID $employeeId is deleting projects with IDs $ids")
    table.deleteByIds(ids).map(_.map(_.toEntity))
  }

  def updateProject(employeeId: Long, project: ProjectEntity): Future[ProjectEntity] = {
    table.queryById(project.id).flatMap {
      case Some(oldRowData: ProjectDBTableRow) =>
        val updatedProject = ProjectEntity.update(employeeId, project, oldRowData)
        table.update(updatedProject).map(_.toEntity)
      case None => throw new NoSuchElementException(s"Project with ID ${project.id} not found")
    }
  }

  def updateProjects(employeeId: Long, projects: Seq[ProjectEntity]): Future[Seq[ProjectEntity]] = {
    table.queryByIds(projects.map(_.id)).flatMap(oldRowDataSeq => {
      val oldRowDataMap = oldRowDataSeq.map(oldRowData => oldRowData.id -> oldRowData).toMap
      val updatedProjectSeq = projects.flatMap { project =>
        oldRowDataMap.get(project.id).map { oldRowData =>
          ProjectEntity.update(employeeId, project, oldRowData)
        }
      }
      table.update(updatedProjectSeq).map(_.map(_.toEntity))
    })
  }

  def getAllProjects(employeeId: Long): Future[Seq[ProjectEntity]] = {
    if (!employeeService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can get all projects.")
    table.query().map(_.map(_.toEntity))
  }

  def getProjectById(employeeId: Long, id: Long): Future[Option[ProjectEntity]] = {
    logger.info(s"Employee with ID $employeeId is getting project with ID $id")
    table.queryById(id).map(_.map(_.toEntity))
  }

  def getProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectEntity]] = {
    logger.info(s"Employee with ID $employeeId is getting projects with IDs $ids")
    table.queryByIds(ids).map(_.map(_.toEntity))
  }

  def getProjectsByEmployeeId(employeeId: Long): Future[Seq[ProjectEntity]] = {
    table.queryByCreatorId(employeeId).map(_.map(_.toEntity))
  }

  def getProjectStatusSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectStatus.values.map(status => {
      val label = status.toString
      val value = ProjectStatus.fromModel(status)
      SelectOption(label, IntValue(value))
    }))
  }

  def getProjectTaskTypeSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectTaskType.values.map(taskType => {
      val label = taskType.toString
      val value = ProjectTaskType.fromModel(taskType)
      SelectOption(label, IntValue(value))
    }))
  }

  def getProjectTaskStatusSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectTaskStatus.values.map(taskStatus => {
      val label = taskStatus.toString
      val value = ProjectTaskStatus.fromModel(taskStatus)
      SelectOption(label, IntValue(value))
    }))
  }
}
