package service

import com.google.inject.Inject
import play.api.libs.json._
import play.api.Logger
import model.{JsonReadWrite, User}


object JsonService {

  var users: List[User] = List()

  def storetolist(user: User) {
    users = users :+ user
  }

  def getJson(): JsValue = {
    Json.toJson(users.map(user => Json.toJson(user)(JsonReadWrite.userWrite)))
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