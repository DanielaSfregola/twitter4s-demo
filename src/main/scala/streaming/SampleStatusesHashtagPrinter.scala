package streaming

import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.Tweet


object SampleStatusesHashtagPrinter extends App {

  val client = TwitterStreamingClient()

  def printHashtags(tweet: Tweet) = tweet.entities match {
    case None => _
    case Some(e) => e.hashtags.foreach { h =>
      println(h.text)
    }
  }

  client.sampleStatuses() {
    case tweet: Tweet => printHashtags(tweet)
  }
}