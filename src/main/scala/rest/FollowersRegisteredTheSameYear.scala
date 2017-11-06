package rest

import java.util.Date

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{RatedData, User}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FollowersRegisteredTheSameYear extends App {

	def getSameRegistrationYearFollowers(creationDate: Date, followers: Set[User]): Set[User] = {
		followers.filter(_.created_at.getYear == creationDate.getYear)
	}

	def printUserRegistration(follower: User): Unit = {
		println(s"${follower.screen_name} created at ${follower.created_at}")
	}

	// TODO - Make sure to define your consumer and access tokens!
	val restClient = TwitterRestClient()

	val userScreenName = "ScalaIO_FR"

	val userEntity: Future[RatedData[User]] = restClient.user(userScreenName)


	val sameYearFollowers: Future[Set[User]] = for {
		user <- userEntity
		followers <- restClient.followersForUser(user.data.screen_name).map(_.data.users)
	} yield getSameRegistrationYearFollowers(user.data.created_at, followers.toSet)

	userEntity.map(rd => {
		printUserRegistration(rd.data)
		println("--")
	})

	sameYearFollowers.map(_.foreach(follower => printUserRegistration(follower)))


}
