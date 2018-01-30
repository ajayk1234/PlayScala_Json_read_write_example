package controllers

import javax.inject.Inject

import play.api.libs.json._
import model.JsonReadWrite._
import play.api.mvc.{AbstractController, ControllerComponents}
import service.JsonService._
class JsonController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  def newUser() = Action { request =>
    Ok(validate(request.body.asJson) match {
      case Left(value)  => storetolist(value)
                           getJson()
      case Right(value) => Json.toJson(value)
    })
  }


  def findUserByName(name: String)=Action {

    getUserByName(name)match{
      case Some(user)=> Ok(Json.toJson(user))
      case _ => Ok("No Found")
    }
  }
  def findUserByMobno(mobno: Long )=Action {

    getUserByMobno(mobno)match{
      case Some(user)=> Ok(Json.toJson(user))
      case _ => Ok("No Found")
    }
  }
}
