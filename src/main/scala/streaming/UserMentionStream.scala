package streaming

import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.entities.enums.WithFilter
import com.danielasfregola.twitter4s.{TwitterRestClient, TwitterStreamingClient}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object UserMentionStream extends App {

  // TODO - Make sure to define your consumer and access tokens!
  val restClient = TwitterRestClient()
  val streamingClient = TwitterStreamingClient()

  def fetchSelfUserId(): Future[Long] =
    restClient.verifyCredentials().map(_.data.id)

  def tweetContainsUserMention(tweet: Tweet, userId: Long): Boolean =
    tweet.entities.exists(_.user_mentions.exists(_.id == userId))

  def selfMentionStream(selfUserId: Long) =
    streamingClient.userEvents(`with` = WithFilter.User) {
      case t: Tweet if tweetContainsUserMention(t, selfUserId) =>
        println(t.text)
    }

  for {
    selfUserId <- fetchSelfUserId()
    stream <- selfMentionStream(selfUserId)
  } yield stream
}
