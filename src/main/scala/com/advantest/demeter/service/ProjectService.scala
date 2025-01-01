package com.advantest.demeter.service

import com.advantest.demeter.DemeterScalaApi.DATABASE_CONFIG.profile.api._
import com.advantest.demeter.DemeterScalaApi.DEMETER_EXECUTION_CONTEXT
import com.advantest.demeter.constant.project.ProjectStatus
import com.advantest.demeter.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.database.table.project.color.ProjectColorDBTable
import com.advantest.demeter.database.table.project.{ProjectDBTable, ProjectDBTableRow}
import com.advantest.demeter.http.payload.{ProjectPayload, ProjectTaskAttributePayload, ProjectTaskPayload}
import com.advantest.demeter.integration.antdesign.select
import com.advantest.demeter.integration.antdesign.select.SelectOption

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
case class ProjectService()(implicit val db: Database) extends Service {
  private val dBTable: ProjectDBTable = ProjectDBTable()
  private val taskService: ProjectTaskService = ProjectTaskService()
  private val colorDBTable: ProjectColorDBTable = ProjectColorDBTable()

  private val employeeService: EmployeeService = EmployeeService()

  private def isCreator(employeeId: Long, projectId: Long): Future[Boolean] = {
    logger.info(s"Checking if employee $employeeId is the creator of project $projectId.")
    dBTable.queryById(projectId).map {
      case Some(value) => value.creatorId == employeeId
      case None => throw new NoSuchElementException(s"Project with ID $projectId not found")
    }
  }

  def createProject(employeeId: Long, project: ProjectPayload): Future[ProjectPayload] = {
    val tableRowData = ProjectPayload.create(employeeId, project)
    dBTable.insert(tableRowData).map(_.toPayload)
  }

  def createProjects(employeeId: Long, projects: Seq[ProjectPayload]): Future[Seq[ProjectPayload]] = {
    val tableRows = projects.map(project => ProjectPayload.create(employeeId, project))
    dBTable.batchInsert(tableRows).map(_.map(_.toPayload))
  }

  def createProjectTask(employeeId: Long, projectId: Long, projectTask: ProjectTaskPayload): Future[ProjectTaskPayload] = {
    taskService.createTask(employeeId, projectId, projectTask)
  }

  def createProjectTasks(employeeId: Long, projectId: Long, projectTasks: Seq[ProjectTaskPayload]): Future[Seq[ProjectTaskPayload]] = {
    taskService.createTasks(employeeId, projectId, projectTasks)
  }

  def createProjectTaskAttribute(employeeId: Long, projectId: Long, projectTaskAttribute: ProjectTaskAttributePayload): Future[ProjectTaskAttributePayload] = {
    taskService.createTaskAttribute(employeeId, projectId, projectTaskAttribute)
  }

  def createProjectTaskAttributes(employeeId: Long, projectId: Long, projectTaskAttributes: Seq[ProjectTaskAttributePayload]): Future[Seq[ProjectTaskAttributePayload]] = {
    taskService.createTaskAttributes(employeeId, projectId, projectTaskAttributes)
  }

  def deleteProjects(employeeId: Long): Future[Seq[ProjectPayload]] = {
    if (!employeeService.checkIfAdmin(employeeId)) throw new SecurityException("Only system admin can delete projects.")
    dBTable.delete().map(_.map(_.toPayload))
  }

  def deleteProjectById(employeeId: Long, id: Long): Future[ProjectPayload] = {
    logger.info(s"Employee with ID $employeeId is deleting project with ID $id")
    dBTable.deleteById(id).map(_.toPayload)
  }

  def deleteProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectPayload]] = {
    logger.info(s"Employee with ID $employeeId is deleting projects with IDs $ids")
    dBTable.deleteByIds(ids).map(_.map(_.toPayload))
  }

  def updateProject(employeeId: Long, project: ProjectPayload): Future[ProjectPayload] = {
    dBTable.queryById(project.id).flatMap {
      case Some(oldRowData: ProjectDBTableRow) =>
        val updatedProject = ProjectPayload.update(employeeId, project, oldRowData)
        dBTable.update(updatedProject).map(_.toPayload)
      case None => throw new NoSuchElementException(s"Project with ID ${project.id} not found")
    }
  }

  def updateProjects(employeeId: Long, projects: Seq[ProjectPayload]): Future[Seq[ProjectPayload]] = {
    dBTable.queryByIds(projects.map(_.id)).flatMap(oldRowDataSeq => {
      val oldRowDataMap = oldRowDataSeq.map(oldRowData => oldRowData.id -> oldRowData).toMap
      val updatedProjectSeq = projects.flatMap { project =>
        oldRowDataMap.get(project.id).map { oldRowData =>
          ProjectPayload.update(employeeId, project, oldRowData)
        }
      }
      dBTable.update(updatedProjectSeq).map(_.map(_.toPayload))
    })
  }

  def getAllProjects(employeeId: Long): Future[Seq[ProjectPayload]] = {
    if (!employeeService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can get all projects.")
    dBTable.query().map(_.map(_.toPayload))
  }

  def getProjectById(employeeId: Long, id: Long): Future[Option[ProjectPayload]] = {
    logger.info(s"Employee with ID $employeeId is getting project with ID $id")
    dBTable.queryById(id).map(_.map(_.toPayload))
  }

  def getProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectPayload]] = {
    logger.info(s"Employee with ID $employeeId is getting projects with IDs $ids")
    dBTable.queryByIds(ids).map(_.map(_.toPayload))
  }

  def getProjectsByEmployeeId(employeeId: Long): Future[Seq[ProjectPayload]] = {
    dBTable.queryByCreatorId(employeeId).map(_.map(_.toPayload))
  }

  def getProjectStatusSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectStatus.values.map(status => {
      val label = status.toString
      val value = ProjectStatus.fromModel(status)
      SelectOption(label, select.IntValue(value))
    }))
  }

  def getProjectTasksByProjectId(employeeId: Long, projectId: Long): Future[Seq[ProjectTaskPayload]] = {
    taskService.getTasksByProjectId(projectId)
  }

  def getProjectTaskAttributesByProjectId(employeeId: Long, projectId: Long): Future[Seq[ProjectTaskAttributePayload]] = {
    taskService.getTaskAttributesByProjectId(projectId)
  }

  def getProjectTaskTypeSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectTaskType.values.map(taskType => {
      val label = taskType.toString
      val value = ProjectTaskType.fromModel(taskType)
      SelectOption(label, select.IntValue(value))
    }))
  }

  def getProjectTaskStatusSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectTaskStatus.values.map(taskStatus => {
      val label = taskStatus.toString
      val value = ProjectTaskStatus.fromModel(taskStatus)
      SelectOption(label, select.IntValue(value))
    }))
  }
}
