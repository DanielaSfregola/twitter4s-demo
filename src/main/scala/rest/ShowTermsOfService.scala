package rest

import com.danielasfregola.twitter4s.TwitterRestClient

import scala.concurrent.ExecutionContext.Implicits.global

object ShowTermsOfService extends App {
  import scala.util.{Success, Failure}

  val client = TwitterRestClient()

  client.termsOfService().onComplete {
    case Success(r) => println(r.data.tos)
    case Failure(t) => println(t.getMessage)
  }
}
