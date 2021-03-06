package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json._
import play.api.mvc.AnyContentAsJson
import play.api.test.Helpers._
import play.api.test._
import play.mvc._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class JsonControllerSpec
    extends PlaySpec
    with GuiceOneAppPerTest
    with Injecting {

  "JsonController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new JsonController(stubControllerComponents())
      val home = controller.index().apply(FakeRequest(GET, "/index"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to Play")
    }

    "render the index page from the application" in {
      val controller = inject[JsonController]
      val home = controller.index().apply(FakeRequest(GET, "/index"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to Play")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/index")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to Play")
    }
  }

  //added two user to JsonController 

  val controller = new JsonController(stubControllerComponents())
  val user1 = controller.newUser().apply(FakeRequest(
    POST,
    "/user",
    FakeHeaders(),
    AnyContentAsJson(Json.parse("""{"user": {"firstName": "cccccc","lastName": "qqq","mobno": 9876543210}}"""))))
  val user2 = controller.newUser().apply(FakeRequest(
    POST,
    "/user",
    FakeHeaders(),
    AnyContentAsJson(Json.parse("""{"user": {"firstName": "ccc","lastName": "qqq","mobno": 9876543210}}"""))))




  "JsonController newUser() POST" should {

    "add new user to list" in {
      val controller = new JsonController(stubControllerComponents())
      val home = controller.newUser().apply(FakeRequest(
        POST,
        "/user",
        FakeHeaders(),
        AnyContentAsJson(Json.parse("""[{"user": {"firstName": "ccc","lastName": "qqq","mobno": 9876543210}}]"""))))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")

    }

  }



  "JsonController getAllUser() GET" should {


    "render the all users " in {


      val home = controller.getAllUser().apply(FakeRequest())

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")

    }

  }

  "JsonController findUserrByName GET" should {


    "render the user object by username" in {

      val home = controller.findUserByName("ccc").apply(FakeRequest())

      contentAsJson(home) mustBe Json.parse("""{"user": {"firstName": "ccc","lastName": "qqq","mobno": 9876543210}}""")
      status(home) mustBe OK

    }

  }

  "JsonController findUserByMobno" should {

    "render the user object by mobile number" in {

      val home = controller.findUserByMobno(9876543210L).apply(FakeRequest())

      contentAsJson(home) mustBe Json.parse("""{"user": {"firstName": "cccccc","lastName": "qqq","mobno": 9876543210}}""")
      status(home) mustBe OK

    }

  }


  "Negative testcase for JsonController newUser POST " should {

    "render Invalid data entered message on screen" in {
      val controller = new JsonController(stubControllerComponents())
      val home = controller.newUser().apply(FakeRequest(
        POST,
        "/user",
        FakeHeaders(),
        AnyContentAsJson(Json.parse("""[{"user": {"firstName": "ccc","lassstName": "qqq","mobno": 9876543210}}]"""))))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include("Invalid")

    }

  }





  "Negative testcase for JsonController findUserrByName GET " should {


    "render not found message on screen" in {

      val home = controller.findUserByName("cccc").apply(FakeRequest())

      status(home) mustBe BAD_REQUEST
      contentAsString(home) must include("Not Found")

    }

  }


  "Negative testcase for  JsonController findUserByMobno GET" should {

    "render not found message on screen" in {


      val home = controller.findUserByMobno(987643210L).apply(FakeRequest())

      status(home) mustBe BAD_REQUEST
      contentAsString(home) must include("Not Found")
    }

  }


}
