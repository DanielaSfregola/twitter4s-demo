import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{HashTag, Tweet}

import scala.concurrent.ExecutionContext.Implicits.global

object UserTopHashtags extends App {

  def getTopHashtags(tweets: Seq[Tweet], n: Int = 10): Seq[(String, Int)] = {
    val hashtags: Seq[Seq[HashTag]] = tweets.map { tweet =>
      tweet.entities.map(_.hashtags).getOrElse(Seq.empty)
    }
    val hashtagTexts: Seq[String] = hashtags.flatten.map(_.text.toLowerCase)
    val hashtagFrequencies: Map[String, Int] = hashtagTexts.groupBy(identity).mapValues(_.size)
    hashtagFrequencies.toSeq.sortBy { case (entity, frequency) => -frequency }.take(n)
  }

  // TODO - Make sure to define your consumer and access tokens!
  val client = TwitterRestClient()

  val user = "odersky"

  client.getUserTimelineForUser(screen_name = user, count = 200).map { tweets =>
    val topHashtags: Seq[((String, Int), Int)] = getTopHashtags(tweets).zipWithIndex
    val rankings = topHashtags.map { case ((entity, frequency), idx) => s"[${idx + 1}] $entity (found $frequency times)"}
    println(s"${user.toUpperCase}'S TOP HASHTAGS:")
    println(rankings.mkString("\n"))
  }

}
