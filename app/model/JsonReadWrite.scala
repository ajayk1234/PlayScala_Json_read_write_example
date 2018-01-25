package model

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.Logger
case class User(val firstName: String, val lastName: String, val mobno: Int)

class JsonReadWrite {

  implicit val userRead = ((__ \ "user" \ "firstName"))
    .read[String]
    .and((__ \ "user" \ "lastName").read[String])
    .and((__ \ "user" \ "mobno").read[Int](min(1000000000)))(User.apply _)

  implicit val userWrite = ((__ \ "user" \ "firstName"))
    .write[String]
    .and((__ \ "user" \ "lastName").write[String])
    .and((__ \ "user" \ "mobno").write[Int])(unlift(User.unapply))

  def validate(jsValue: Option[JsValue]): Either[User, String] = {
    val jsvalue = Json.toJson(jsValue)
    jsvalue.validate[User] match {
      case JsSuccess(user: User, __) =>
        Left(user)
      case _ =>
        Right("Not A proper Format")
    }

  }
  //Converts the Object to Json takes Implicit write function
  def getJson(user: User): JsValue = {
    Json.toJson(user)
  }

}
