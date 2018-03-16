package rest

import com.danielasfregola.twitter4s.TwitterRestClient

import scala.concurrent.ExecutionContext.Implicits.global

object ShowPrivacyPolicy extends App {
  import scala.util.{Success, Failure}

  val client = TwitterRestClient()

  client.privacyPolicy().onComplete {
    case Success(r) => println(r.data.privacy)
    case Failure(t) => println(t.getMessage)
  }
}
