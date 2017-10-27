package streaming

import com.danielasfregola.twitter4s.{TwitterRestClient, TwitterStreamingClient}
import com.danielasfregola.twitter4s.entities.Tweet
import scala.concurrent.ExecutionContext.Implicits.global

object StreamMentionReplyier extends App {

  // TODO - Make sure to define your consumer and access tokens with write permissions!
  val streamingClient = TwitterStreamingClient()
  val restClient = TwitterRestClient()

  def favoriteIfHashtag(tweet: Tweet) = tweet.entities match {
    case None => ()
    case Some(e) => if(e.hashtags.exists(_.text.equalsIgnoreCase("scala"))) favoriteTweet(tweet)
  }

  def favoriteTweet(tweet: Tweet) = restClient.favoriteStatus(tweet.id).onComplete(_ => println("new tweet marked as favorite!"))

  streamingClient.userEvents() {
    case tweet: Tweet => favoriteIfHashtag(tweet)
  }
}