package rest

import com.danielasfregola.twitter4s.TwitterRestClient

import scala.concurrent.ExecutionContext.Implicits.global

object LastTweetInTimeline extends App {

  // TODO - Make sure to define your consumer and access tokens!
  val client = TwitterRestClient()

  client.homeTimeline(count = 1).map { ratedData =>
    val tweet = ratedData.data.headOption
    tweet match {
      case Some(t) => println(t.text)
      case None => println("No tweets in timeline")
    }
  }
}
