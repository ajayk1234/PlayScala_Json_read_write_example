package controllers

import javax.inject.Inject
import play.api.libs.json._
import model.JsonReadWrite
import play.api.mvc.{AbstractController, ControllerComponents}

class JsonController @Inject()(cc: ControllerComponents)(jsonrw: JsonReadWrite)
    extends AbstractController(cc) {

  def jsonRead() = Action { request =>
    Ok(jsonrw.validate(request.body.asJson) match {
      case Left(value)  => jsonrw.getJson(value)
      case Right(value) => Json.toJson(value)
    })
  }
}
