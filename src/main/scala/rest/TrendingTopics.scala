package rest

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, LocationTrends}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Success, Try}

/**
  * Created by Joseph on 18/02/2017.
  */
object TrendingTopics extends App {

  // TODO - Make sure to define your consumer and access tokens!
  val client = TwitterRestClient()

  def printTrendingTopics(locationTrends: Seq[LocationTrends]): Unit =
    locationTrends.foreach { locationTrend =>
      println()
      println("Trends for: " + locationTrend.locations.map(_.name).mkString("/"))
      locationTrend.trends.take(10).foreach(t => println(t.name))
    }

  lazy val globalTrends = for {
    globalTrendsResult <- client.globalTrends()
  } yield printTrendingTopics(globalTrendsResult)

  lazy val ukTrends = for {
    locations <- client.locationTrends()
    ukWoeid = locations.find(_.name == "United Kingdom").map(_.woeid)
    if ukWoeid.isDefined
    ukTrendsResult <- client.trends(ukWoeid.get)
  } yield printTrendingTopics(ukTrendsResult)

  lazy val nearMeTrends = for {
    locationNearestMe <- client.closestLocationTrends(43.6426, -79.3871)
    woeId = locationNearestMe.headOption.map(_.woeid)
    if woeId.isDefined
    locationNearestMeResult <- client.trends(woeId.get)
  } yield printTrendingTopics(locationNearestMeResult)

  val allTheTrends = for {
    _ <- globalTrends
    _ <- ukTrends
    _ <- nearMeTrends
  } yield ()
  try Await.result(allTheTrends, Duration.Inf)
  finally client.close

}
