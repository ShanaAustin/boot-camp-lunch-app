package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers.{status, _}
import play.api.mvc.{Action, Controller}

class WelcomeController extends Controller {
  def welcome() = Action {
    Ok
  }
}



class WelcomeControllerSpec extends PlaySpec with GuiceOneAppPerTest {
  "WelcomeController GET" should {
    "return a successful response" in {
      val controller = new WelcomeController
      val result = controller.welcome().apply(FakeRequest(GET, "/foo"))
      status(result) mustBe OK
    }
    "respond to the /welcome url" in {
      // Need to specify Host header to get through AllowedHostsFilter
      val request = FakeRequest(GET, "/welcome").withHeaders("Host" -> "localhost")
      val home = route(app, request).get
      status(home) mustBe OK
    }
    "return some html" in {
      val controller = new WelcomeController
      val result = controller.welcome().apply(FakeRequest(GET, "/foo"))
      contentType(result) mustBe Some("text/html")
    }

    "say hello and have a title" in {
      val controller = new WelcomeController
      val result = controller.welcome().apply(FakeRequest(GET, "/foo"))
      contentAsString(result) must include ("<h1>Hello!</h1>")
      contentAsString(result) must include ("<title>Welcome!</title>")
    }
  }
}


class HomeControllerSpec extends PlaySpec with OneAppPerTest {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController
      val home = controller.index().apply(FakeRequest())

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "render the index page from the application" in {
      val controller = app.injector.instanceOf[HomeController]
      val home = controller.index().apply(FakeRequest())

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "render the index page from the router" in {
      // Need to specify Host header to get through AllowedHostsFilter
      val request = FakeRequest(GET, "/").withHeaders("Host" -> "localhost")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }
  }
}
