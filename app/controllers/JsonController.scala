package controllers

import javax.inject.Inject

import play.api.libs.json._
import model.JsonReadWrite._
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import service.JsonService._
class JsonController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  def newUser() = Action { request =>


    Ok(validate(request.body.asJson) match {
      case Left(value)  => storetolist(value)
      case Right(value) => Json.toJson(value)
    })
  }


  def getAllUser() = Action { request =>
      Ok(getJson())
  }
  def findUserByName(name: String)=Action {

    getUserByName(name)match{
      case Some(user)=> Ok(Json.toJson(user))
      case _ => BadRequest("Not Found")
    }
  }
  def findUserByMobno(mobno: Long )=Action {

    getUserByMobno(mobno)match{
      case Some(user)=> Ok(Json.toJson(user))
      case _ => BadRequest("Not Found")
    }
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
