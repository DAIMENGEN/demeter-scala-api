package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.core.entity.project.ProjectEntity
import com.advantest.demeter.core.service.ProjectService
import com.advantest.demeter.utils.http.{ApiRequest, ApiResponse, HttpRoute}
import spray.json.DefaultJsonProtocol._

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
case class ProjectRoute() extends HttpRoute with ApiRequest with ApiResponse {
  private val projectService: ProjectService = ProjectService()

  override def route: Route = concat(
    createProjectRoute,
    createProjectsRoute,
    deleteProjectsRoute(),
    deleteProjectByIdRoute(),
    deleteProjectsByIdsRoute(),
    updateProjectRoute(),
    updateProjectsRoute(),
    getAllProjectsRoute,
    getProjectByIdRoute,
    getProjectsByIdsRoute,
    getProjectsByEmployeeIdRoute,
    getProjectTaskFieldsByProjectIdRoute,
    getProjectStatusSelectOptionsRoute,
    getProjectTaskTypeSelectOptionsRoute,
    getProjectTaskStatusSelectOptionsRoute
  )

  private def createProjectRoute: Route = path("createProjectRoute") {
    post {
      validateToken { employee =>
        entity(as[ProjectEntity]) {
          request =>
            val future = projectService.createProject(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def createProjectsRoute: Route = path("createProjectsRoute") {
    post {
      validateToken { employee =>
        entity(as[Seq[ProjectEntity]]) {
          request =>
            val future = projectService.createProjects(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def deleteProjectsRoute(): Route = path("deleteProjectsRoute") {
    post {
      validateToken { employee =>
        val future = projectService.deleteProjects(employee.id)
        response(future)
      }
    }
  }

  private def deleteProjectByIdRoute(): Route = path("deleteProjectByIdRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val future = projectService.deleteProjectById(employee.id, projectId)
            response(future)
        }
      }
    }
  }

  private def deleteProjectsByIdsRoute(): Route = path("deleteProjectsByIdsRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectIds = request.readLongArray("projectIds")
            val future = projectService.deleteProjectsByIds(employee.id, projectIds)
            response(future)
        }
      }
    }
  }

  private def updateProjectRoute(): Route = path("updateProjectRoute") {
    put {
      validateToken { employee =>
        entity(as[ProjectEntity]) {
          request =>
            val future = projectService.updateProject(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def updateProjectsRoute(): Route = path("updateProjectsRoute") {
    put {
      validateToken { employee =>
        entity(as[Seq[ProjectEntity]]) {
          request =>
            val future = projectService.updateProjects(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def getAllProjectsRoute: Route = path("getAllProjectsRoute") {
    get {
      validateToken { employee =>
        val future = projectService.getAllProjects(employee.id)
        response(future)
      }
    }
  }

  private def getProjectByIdRoute: Route = path("getProjectByIdRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val future = projectService.getProjectById(employee.id, projectId)
            response(future)
        }
      }
    }
  }

  private def getProjectsByIdsRoute: Route = path("getProjectsByIdsRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectIds = request.readLongArray("projectIds")
            val future = projectService.getProjectsByIds(employee.id, projectIds)
            response(future)
        }
      }
    }
  }

  private def getProjectsByEmployeeIdRoute: Route = path("getProjectsByEmployeeIdRoute") {
    get {
      validateToken { employee =>
        val future = projectService.getProjectsByEmployeeId(employee.id)
        response(future)
      }
    }
  }

  private def getProjectTaskFieldsByProjectIdRoute: Route = path("getProjectTaskFieldsByProjectIdRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val future = projectService.getProjectTaskFieldsByProjectId(employee.id, projectId)
            response(future)
        }
      }
    }
  }

  private def getProjectStatusSelectOptionsRoute: Route = path("getProjectStatusSelectOptionsRoute") {
    get {
      val future = projectService.getProjectStatusSelectOptions
      response(future)
    }
  }

  private def getProjectTaskTypeSelectOptionsRoute: Route = path("getProjectTaskTypeSelectOptionsRoute") {
    get {
      val future = projectService.getProjectTaskTypeSelectOptions
      response(future)
    }
  }

  private def getProjectTaskStatusSelectOptionsRoute: Route = path("getProjectTaskStatusSelectOptionsRoute") {
    get {
      val future = projectService.getProjectTaskStatusSelectOptions
      response(future)
    }
  }
}
