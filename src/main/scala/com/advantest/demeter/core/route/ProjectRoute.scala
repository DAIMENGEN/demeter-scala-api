package com.advantest.demeter.core.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.advantest.demeter.core.http.payload.{ProjectPayload, ProjectTaskAttributePayload, ProjectTaskPayload}
import com.advantest.demeter.core.service.ProjectService
import com.advantest.demeter.utils.http.{ApiRequest, ApiResponse, HttpRoute}
import slick.jdbc.MySQLProfile.api._
import spray.json.DefaultJsonProtocol._

/**
 * Create on 2024/10/27
 * Author: mengen.dai@outlook.com
 */
case class ProjectRoute()(implicit val db: Database) extends HttpRoute with ApiRequest with ApiResponse {
  private val projectService: ProjectService = ProjectService()

  override def route: Route = concat(
    createProjectRoute,
    createProjectsRoute,
    createProjectTaskRoute,
    createProjectTasksRoute,
    createProjectTaskAttributeRoute,
    createProjectTaskAttributesRoute,
    deleteProjectsRoute(),
    deleteProjectByIdRoute(),
    deleteProjectsByIdsRoute(),
    updateProjectRoute(),
    updateProjectsRoute(),
    getAllProjectsRoute,
    getProjectByIdRoute,
    getProjectsByIdsRoute,
    getProjectsByEmployeeIdRoute,
    getProjectStatusSelectOptionsRoute,
    getProjectTasksByProjectIdRoute,
    getProjectTaskAttributesByProjectIdRoute,
    getProjectTaskTypeSelectOptionsRoute,
    getProjectTaskStatusSelectOptionsRoute
  )

  private def createProjectRoute: Route = path("createProjectRoute") {
    post {
      validateToken { employee =>
        entity(as[ProjectPayload]) {
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
        entity(as[Seq[ProjectPayload]]) {
          request =>
            val future = projectService.createProjects(employee.id, request)
            response(future)
        }
      }
    }
  }

  private def createProjectTaskRoute: Route = path("createProjectTaskRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val projectTask = request.read[ProjectTaskPayload]("projectTask")
            val future = projectService.createProjectTask(employee.id, projectId, projectTask)
            response(future)
        }
      }
    }
  }

  private def createProjectTasksRoute: Route = path("createProjectTasksRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val projectTasks = request.read[Seq[ProjectTaskPayload]]("projectTasks")
            val future = projectService.createProjectTasks(employee.id, projectId, projectTasks)
            response(future)
        }
      }
    }
  }

  private def createProjectTaskAttributeRoute: Route = path("createProjectTaskAttributeRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val projectTaskAttribute = request.read[ProjectTaskAttributePayload]("projectTaskAttribute")
            val future = projectService.createProjectTaskAttribute(employee.id, projectId, projectTaskAttribute)
            response(future)
        }
      }
    }
  }

  private def createProjectTaskAttributesRoute: Route = path("createProjectTaskAttributesRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val projectTaskAttributes = request.read[Seq[ProjectTaskAttributePayload]]("projectTaskAttributes")
            val future = projectService.createProjectTaskAttributes(employee.id, projectId, projectTaskAttributes)
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
        entity(as[ProjectPayload]) {
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
        entity(as[Seq[ProjectPayload]]) {
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

  private def getProjectStatusSelectOptionsRoute: Route = path("getProjectStatusSelectOptionsRoute") {
    get {
      val future = projectService.getProjectStatusSelectOptions
      response(future)
    }
  }

  private def getProjectTasksByProjectIdRoute: Route = path("getProjectTasksByProjectIdRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val future = projectService.getProjectTasksByProjectId(employee.id, projectId)
            response(future)
        }
      }
    }
  }

  private def getProjectTaskAttributesByProjectIdRoute: Route = path("getProjectTaskAttributesByProjectIdRoute") {
    post {
      validateToken { employee =>
        entity(as[HttpRequestParams]) {
          request =>
            val projectId = request.readLong("projectId")
            val future = projectService.getProjectTaskAttributesByProjectId(employee.id, projectId)
            response(future)
        }
      }
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
