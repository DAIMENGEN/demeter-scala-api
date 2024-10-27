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
    getProjectsByIdsRoute
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
    delete {
      validateToken { employee =>
        val future = projectService.deleteProjects(employee.id)
        response(future)
      }
    }
  }

  private def deleteProjectByIdRoute(): Route = path("deleteProjectByIdRoute") {
    delete {
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
    delete {
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
    get {
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
    get {
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
}
