
import com.danielasfregola.twitter4s.TwitterClient
import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.entities.enums.ResultType
import com.typesafe.config.ConfigFactory
import utils.FileSupport

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object ScalaXDownloadTweets extends App with FileSupport {

  // TODO - Make sure to change the application.conf file with the right consumer and access tokens!
  val client = new TwitterClient()

  def searchTweets(query: String, max_id: Option[Long] = None): Future[Seq[Tweet]] = {
    def extractNextMaxId(params: String): Option[Long] = {
      //example: "?max_id=658200158442790911&q=%23scala&include_entities=1&result_type=mixed"
      params.split("&").find(_.contains("max_id")).map(_.split("=")(1).toLong)
    }

    client.searchTweet(query, count = 100, result_type = ResultType.Recent, max_id = max_id).flatMap { result =>
        val nextMaxId = extractNextMaxId(result.search_metadata.next_results)
        val tweets = result.statuses
        if (!tweets.isEmpty) searchTweets(query, nextMaxId).map(_ ++ tweets)
        else Future(tweets.sortBy(_.created_at))
      } recover { case _ => Seq.empty }
  }

  val filename = {
    val config = ConfigFactory.load()
    config.getString("tweets.scalax")
  }

  searchTweets("#scalax").map { tweets =>
    println(s"Downloaded ${tweets.size} tweets")
    toFileAsJson(filename, tweets)
    println(s"Tweets saved to file $filename")
  }

}
