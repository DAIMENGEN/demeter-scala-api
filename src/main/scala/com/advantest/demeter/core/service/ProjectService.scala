package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.constant.project.ProjectStatus
import com.advantest.demeter.core.constant.project.permission.{ProjAdminPerm, ProjViewPerm}
import com.advantest.demeter.core.constant.project.task.{ProjectTaskStatus, ProjectTaskType}
import com.advantest.demeter.core.database.project.color.ProjectColorTable
import com.advantest.demeter.core.database.project.permission.ProjectPermissionTable
import com.advantest.demeter.core.database.project.task.ProjectTaskTable
import com.advantest.demeter.core.database.project.{ProjectTable, ProjectTableRow}
import com.advantest.demeter.core.entity.project.ProjectEntity
import com.advantest.demeter.integration.antdesign.select.SelectOption

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
case class ProjectService() extends Service {
  private val table: ProjectTable = ProjectTable()
  private val taskTable: ProjectTaskTable = ProjectTaskTable()
  private val colorTable: ProjectColorTable = ProjectColorTable()
  private val permissionTable: ProjectPermissionTable = ProjectPermissionTable()

  private val employeeService: EmployeeService = EmployeeService()

  private def isCreator(employeeId: Long, projectId: Long): Future[Boolean] = {
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
    val isAdmin = employeeService.checkIfAdmin(employeeId)
    for {
      isCreator <- isCreator(employeeId, id)
      hasDeletePermission <- if (isAdmin) {
        Future.successful(true)
      } else {
        permissionTable.queryByEmployeeIdAndProjectId(employeeId, id).map(_.exists(row => row.permission.equals(ProjAdminPerm)))
      }
      result <- if (isCreator || hasDeletePermission) {
        table.deleteById(id).map(_.toEntity)
      } else {
        throw new SecurityException("Sorry, You do not have the necessary permission to delete the current project.")
      }
    } yield result
  }

  def deleteProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectEntity]] = {
    val isAdmin = employeeService.checkIfAdmin(employeeId)
    for {
      canDelete <- if (isAdmin) {
        Future.successful(true)
      } else {
        permissionTable.queryByEmployeeIdAndProjectIds(employeeId, ids)
          .map(_.filter(row => row.permission.equals(ProjAdminPerm)).map(_.projectId).toSet)
          .map(_.equals(ids.toSet))
      }
      result <- if (canDelete) {
        table.deleteByIds(ids).map(_.map(_.toEntity))
      } else {
        throw new SecurityException("Sorry, You do not have the necessary permission to delete those projects.")
      }
    } yield result
  }

  def updateProject(employeeId: Long, project: ProjectEntity): Future[ProjectEntity] = {
    val isAdmin = employeeService.checkIfAdmin(employeeId)
    for {
      isCreator <- isCreator(employeeId, project.id)
      hasUpdatePermission <- if (isAdmin) {
        Future.successful(true)
      } else {
        permissionTable.queryByEmployeeIdAndProjectId(employeeId, project.id).map(_.exists(row => row.permission.equals(ProjAdminPerm)))
      }
      result <- if (isCreator || hasUpdatePermission) {
        table.queryById(project.id).flatMap {
          case Some(oldRowData: ProjectTableRow) =>
            val updatedProject = ProjectEntity.update(employeeId, project, oldRowData)
            table.update(updatedProject).map(_.toEntity)
          case None => throw new NoSuchElementException(s"Project with ID ${project.id} not found")
        }
      } else {
        throw new SecurityException("Sorry, You do not have the necessary permission to update the current project.")
      }
    } yield result
  }

  def updateProjects(employeeId: Long, projects: Seq[ProjectEntity]): Future[Seq[ProjectEntity]] = {
    val isAdmin = employeeService.checkIfAdmin(employeeId)
    for {
      canUpdate <- if (isAdmin) {
        Future.successful(true)
      } else {
        permissionTable.queryByEmployeeIdAndProjectIds(employeeId, projects.map(_.id)).map(_.filter(row => row.permission.equals(ProjAdminPerm)).map(_.projectId).toSet)
          .map(_.equals(projects.map(_.id).toSet))
      }
      result <- if (canUpdate) {
        table.queryByIds(projects.map(_.id)).flatMap(oldRowDataSeq => {
          val oldRowDataMap = oldRowDataSeq.map(oldRowData => oldRowData.id -> oldRowData).toMap
          val updatedProjectSeq = projects.flatMap { project =>
            oldRowDataMap.get(project.id).map { oldRowData =>
              ProjectEntity.update(employeeId, project, oldRowData)
            }
          }
          table.update(updatedProjectSeq).map(_.map(_.toEntity))
        })
      } else {
        throw new SecurityException("Sorry, You do not have the necessary permission to update those projects.")
      }
    } yield result
  }

  def getAllProjects(employeeId: Long): Future[Seq[ProjectEntity]] = {
    if (!employeeService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can get all projects.")
    table.query().map(_.map(_.toEntity))
  }

  def getProjectById(employeeId: Long, id: Long): Future[Option[ProjectEntity]] = {
    val isAdmin = employeeService.checkIfAdmin(employeeId)
    for {
      isCreator <- isCreator(employeeId, id)
      hasViewPermission <- if (isAdmin) {
        Future.successful(true)
      } else {
        permissionTable.queryByEmployeeIdAndProjectId(employeeId, id).map(_.exists(row => row.permission.equals(ProjViewPerm)))
      }
      result <- if (isCreator || hasViewPermission) {
        table.queryById(id).map(_.map(_.toEntity))
      } else {
        throw new SecurityException("Sorry, You do not have the necessary permission to get the current project.")
      }
    } yield result
  }

  def getProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectEntity]] = {
    val isAdmin = employeeService.checkIfAdmin(employeeId)
    for {
      canRead <- if (isAdmin) {
        Future.successful(true)
      } else {
        permissionTable.queryByEmployeeIdAndProjectIds(employeeId, ids)
          .map(_.filter(row => row.permission.equals(ProjViewPerm)).map(_.projectId).toSet)
          .map(_.equals(ids.toSet))
      }
      result <- if (canRead) {
        table.queryByIds(ids).map(_.map(_.toEntity))
      } else {
        throw new SecurityException("Sorry, You do not have the necessary permission to get those projects.")
      }
    } yield result
  }

  def getProjectsByEmployeeId(employeeId: Long): Future[Seq[ProjectEntity]] = {
    val isAdmin = employeeService.checkIfAdmin(employeeId)
    if (isAdmin) {
      table.query().map(_.map(_.toEntity))
    } else {
      val hasPermissionProjects = permissionTable.queryByEmployeeId(employeeId).map(_.map(row => row.projectId)).flatMap(projectIds => {
        table.queryByIds(projectIds).map(_.map(_.toEntity))
      })
      val createProjects = table.queryByCreatorId(employeeId).map(_.map(_.toEntity))
      for {
        h <- hasPermissionProjects
        c <- createProjects
      } yield h ++ c
    }
  }

  def getProjectStatusSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectStatus.values.map(status => {
      val label = status.toString
      val value = ProjectStatus.fromModel(status)
      SelectOption(label, value)
    }))
  }

  def getProjectTaskTypeSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectTaskType.values.map(taskType => {
      val label = taskType.toString
      val value = ProjectTaskType.fromModel(taskType)
      SelectOption(label, value)
    }))
  }

  def getProjectTaskStatusSelectOptions: Future[Seq[SelectOption]] = {
    Future.successful(ProjectTaskStatus.values.map(taskStatus => {
      val label = taskStatus.toString
      val value = ProjectTaskStatus.fromModel(taskStatus)
      SelectOption(label, value)
    }))
  }
}
