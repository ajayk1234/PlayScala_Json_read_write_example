package controllers

import javax.inject._

import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.I18nSupport
import play.api.mvc._
import views.html.helper.form

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

case class UserData(email: String, password: String)

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  val userDataForm: Form[UserData] = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
  )(UserData.apply)(UserData.unapply)
  )

  def login = Action { implicit request =>
    Ok(views.html.index(userDataForm))
  }

  def doLogin = Action { implicit request =>

    val user = userDataForm.bindFromRequest.get


    Ok(user.email)
  }


  def index = Action { implicit request =>
    Ok("index")
  }
}

