package model

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.Logger
case class User(val firstName: String, val lastName: String, val mobno: Long)

object JsonReadWrite {

  var users: List[User] = List()
  implicit val userRead = ((__ \ "user" \ "firstName"))
    .read[String]
    .and((__ \ "user" \ "lastName").read[String])
    .and((__ \ "user" \ "mobno").read[Long])(User.apply _)

  implicit val userWrite = ((__ \ "user" \ "firstName"))
    .write[String]
    .and((__ \ "user" \ "lastName").write[String])
    .and((__ \ "user" \ "mobno").write[Long])(unlift(User.unapply))

  def validate(jsValue: Option[JsValue]): Either[User, String] = {
    val jsvalue = Json.toJson(jsValue)
    jsvalue.validate[User] match {
      case JsSuccess(user: User, __) =>
        Left(user)
      case _ =>
        Right("Invalid")
    }

  }

  def getJson(): JsValue = {
    Json.toJson(users)
  }

  def storetolist(user: User) {
    users = users :+ user
  }

  def getUserByName(name: String):Option[User] = {
    for (user <- users) {
      if (name == user.firstName) {
        Logger.debug("########## FOUND ############")
        return Some(user)
      }
    }
    return None
  }

  def getUserByMobno(mobno: Long): Option[User] = {
    for (user <- users) {
      if (mobno == user.mobno) {
        Logger.debug("########## FOUND ############")
        return Some(user)
      }
    }
    return None
  }

}