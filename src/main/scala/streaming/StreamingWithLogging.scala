package streaming

import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.processors.TwitterProcessor._
import com.typesafe.scalalogging.LazyLogging


object StreamingWithLogging extends App with LazyLogging {

  // Make sure to define the following env variables:
  // TWITTER_CONSUMER_TOKEN_KEY and TWITTER_CONSUMER_TOKEN_SECRET
  // TWITTER_ACCESS_TOKEN_KEY and TWITTER_ACCESS_TOKEN_SECRET
  val streamingClient = TwitterStreamingClient()

  // change this to whichever words you want to track
  val trackedWords = Seq("#scala", "#functionalprogramming", "#haskell")

  // logger is accessible with trait LazyLogging - see https://github.com/typesafehub/scala-logging
  // backend used in project is logback - https://logback.qos.ch/
  // configuration for logback is logback.xml in resources
  logger.info(s"Launching streaming session with tracked keywords: $trackedWords")

  // demo purpose only, do not use logging to serialize tweets :)
  streamingClient.filterStatuses(tracks = trackedWords) {
    case tweet: Tweet =>
      // will log to console Tweet text - check TwitterProcessor for details
      // will log to file StreamingClient debug message because debug level is enabled in logback.xml
      logTweetText(tweet)
  }

}