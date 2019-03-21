package rest
package cursored

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FollowersForUser extends App {

  val restClient = TwitterRestClient()

  def followersForUser(screenName: String) = {

    def apiCall(cursor: Long) =
      restClient.followersForUser(screen_name = screenName, cursor = cursor, count = 199, skip_status = true)

    def inner(cursor: Long = -1, accum: Seq[User] = Seq.empty[User]): Future[Seq[User]] = {
      apiCall(cursor).flatMap { data =>
        if (data.data.next_cursor == 0) {
          Future { data.data.users ++ accum }
        } else {
          inner(data.data.next_cursor, data.data.users ++ accum)
        }
      }
    }

    inner()
  }

  followersForUser("mfirry").map { f =>
    f.map(_.screen_name)
  }

}
