package rest

import java.time.{Instant, LocalDateTime, ZoneOffset}

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FollowersRegisteredTheSameYear extends App {

  def getSameRegistrationYearFollowers(creation: Instant, followers: Set[User]): Set[User] = {
    def getYear(instant: Instant): Int = LocalDateTime.ofInstant(creation, ZoneOffset.UTC).getYear

    followers.filter(u => getYear(u.created_at) == getYear(creation))
  }

  def printUserRegistration(follower: User): Unit = {
    println(s"${follower.screen_name} created at ${follower.created_at}")
  }

  // Make sure to define your consumer and access tokens via ENV variables!
  val restClient     = TwitterRestClient()
  val userScreenName = "ScalaIO_FR"

  val sameYearFollowers: Future[Set[User]] = for {
    user      <- restClient.user(userScreenName)
    followers <- restClient.followersForUser(user.data.screen_name).map(_.data.users)
  } yield getSameRegistrationYearFollowers(user.data.created_at, followers.toSet)

  sameYearFollowers.foreach(_.foreach(follower => printUserRegistration(follower)))

}
