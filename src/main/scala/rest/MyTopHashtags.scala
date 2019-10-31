package rest

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{HashTag, Tweet}

import scala.concurrent.ExecutionContext.Implicits.global

object MyTopHashtags extends App {

  def getTopHashtags(tweets: Seq[Tweet], n: Int = 10): Seq[(String, Int)] = {
    val hashtags: Seq[Seq[HashTag]] = tweets.map { tweet =>
      tweet.entities.map(_.hashtags).getOrElse(List.empty)
    }
    val hashtagTexts: Seq[String]            = hashtags.flatten.map(_.text.toLowerCase)
    val hashtagFrequencies: Map[String, Int] = hashtagTexts.groupBy(identity).map { case (k, v) => k -> v.size }
    hashtagFrequencies.toSeq.sortBy { case (_, frequency) => -frequency }.take(n)
  }

  // TODO - Make sure to define your consumer and access tokens!
  val client = TwitterRestClient()

  val result = client.homeTimeline(count = 200).map { ratedData =>
    val tweets                                 = ratedData.data
    val topHashtags: Seq[((String, Int), Int)] = getTopHashtags(tweets).zipWithIndex
    val rankings = topHashtags.map {
      case ((entity, frequency), idx) => s"[${idx + 1}] $entity (found $frequency times)"
    }
    println("MY TOP HASHTAGS:")
    println(rankings.mkString("\n"))
  }
}
