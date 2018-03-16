package rest

import java.util.{Calendar, Date}

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FollowersRegisteredTheSameYear extends App {
  def getSameRegistrationYearFollowers(creationDate: Date, followers: Set[User]): Set[User] = {
    def getYear(date: Date): Int =
      (new Calendar.Builder()).setInstant(date).build().get(Calendar.YEAR) - 1900

    followers.filter(u => getYear(u.created_at) == getYear(creationDate))
  }

  def printUserRegistration(follower: User): Unit = {
    println(s"${follower.screen_name} created at ${follower.created_at}")
  }

  // TODO - Make sure to define your consumer and access tokens!
  val restClient     = TwitterRestClient()
  val userScreenName = "ScalaIO_FR"

  val sameYearFollowers: Future[Set[User]] = for {
    user      <- restClient.user(userScreenName)
    followers <- restClient.followersForUser(user.data.screen_name).map(_.data.users)
  } yield getSameRegistrationYearFollowers(user.data.created_at, followers.toSet)

  sameYearFollowers.foreach(_.foreach(follower => printUserRegistration(follower)))

}
