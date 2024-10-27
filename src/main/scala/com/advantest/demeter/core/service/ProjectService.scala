package com.advantest.demeter.core.service

import com.advantest.demeter.DemeterScalaApi.{DEMETER_DATABASE, DEMETER_EXECUTION_CONTEXT}
import com.advantest.demeter.core.database.project.{ProjectTable, ProjectTableRow}
import com.advantest.demeter.core.entity.project.ProjectEntity

import scala.concurrent.Future

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
case class ProjectService() extends Service {
  private val projectTable: ProjectTable = ProjectTable()
  private val employeeService: EmployeeService = EmployeeService()

  def createProject(employeeId: Long, project: ProjectEntity): Future[ProjectEntity] = {
    val tableRowData = ProjectEntity.create(employeeId, project)
    projectTable.insert(tableRowData).map(_.toEntity)
  }

  def createProjects(employeeId: Long, projects: Seq[ProjectEntity]): Future[Seq[ProjectEntity]] = {
    val tableRows = projects.map(project => ProjectEntity.create(employeeId, project))
    projectTable.batchInsert(tableRows).map(_.map(_.toEntity))
  }

  def deleteProjects(employeeId: Long): Future[Seq[ProjectEntity]] = {
    if (employeeService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can delete projects.")
    projectTable.delete().map(_.map(_.toEntity))
  }

  def deleteProjectById(employeeId: Long, id: Long): Future[ProjectEntity] = {
    // TODO 检查当前员工是否有权限删除当前的项目
    projectTable.deleteById(id).map(_.toEntity)
  }

  def deleteProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectEntity]] = {
    // TODO 检查当前员工是否有权限删除当前的项目
    projectTable.deleteByIds(ids).map(_.map(_.toEntity))
  }

  def updateProject(employeeId: Long, project: ProjectEntity): Future[ProjectEntity] = {
    projectTable.queryById(project.id).flatMap {
      case Some(oldRowData: ProjectTableRow) =>
        val updatedProject = ProjectEntity.update(employeeId, project, oldRowData)
        projectTable.update(updatedProject).map(_.toEntity)
      case None => throw new Exception("project not found")
    }
  }

  def updateProjects(employeeId: Long, projects: Seq[ProjectEntity]): Future[Seq[ProjectEntity]] = {
    projectTable.queryByIds(projects.map(_.id)).flatMap(oldRowDataSeq => {
      val oldRowDataMap = oldRowDataSeq.map(oldRowData => oldRowData.id -> oldRowData).toMap
      val updatedProjectSeq = projects.flatMap { project =>
        oldRowDataMap.get(project.id).map { oldRowData =>
          ProjectEntity.update(employeeId, project, oldRowData)
        }
      }
      projectTable.update(updatedProjectSeq).map(_.map(_.toEntity))
    })
  }

  def getAllProjects(employeeId: Long): Future[Seq[ProjectEntity]] = {
    if (employeeService.checkIfAdmin(employeeId)) throw new IllegalArgumentException("Only system admin can get all projects.")
    projectTable.query().map(_.map(_.toEntity))
  }

  def getProjectById(employeeId: Long, id: Long): Future[Option[ProjectEntity]] = {
    // TODO 检查当前员工是否有权限获取当前的项目
    projectTable.queryById(id).map(_.map(_.toEntity))
  }

  def getProjectsByIds(employeeId: Long, ids: Seq[Long]): Future[Seq[ProjectEntity]] = {
    // TODO 检查当前员工是否有权限获取当前的项目
    projectTable.queryByIds(ids).map(_.map(_.toEntity))
  }
}
