package streaming

import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.Tweet

object TweetTimelineStreamPrinter extends App {

  // TODO - Make sure to define your consumer and access tokens!
  val client = TwitterStreamingClient()

  client.userEvents() {
    case tweet: Tweet => println(tweet.text)
  }
}
