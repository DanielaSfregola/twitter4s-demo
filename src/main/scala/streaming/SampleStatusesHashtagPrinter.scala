package streaming

import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.Tweet

object SampleStatusesHashtagPrinter extends App {

  // TODO - Make sure to define your consumer and access tokens!
  val client = TwitterStreamingClient()

  def printHashtags(tweet: Tweet) = tweet.entities.map { e =>
    e.hashtags.foreach { h =>
      println(h.text)
    }
  }

  def filterTweetByHashtag(tweet: Tweet, myAwesomeHashtag: String): Option[Tweet] = tweet.entities.flatMap { e =>
    val hashtagTexts = e.hashtags.map(_.text.toUpperCase)
    if (hashtagTexts.contains(myAwesomeHashtag.toUpperCase)) Some(tweet)
    else None
  }

  client.firehoseStatuses() {
    case tweet: Tweet => filterTweetByHashtag(tweet, "scala") map printHashtags
  }
}
