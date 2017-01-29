package rest

import com.danielasfregola.twitter4s.entities.Tweet
import com.typesafe.config.ConfigFactory
import rest.utils.FileSupport

object SimpleStatistics extends App with FileSupport {

  type Record[T] = (T, Int)

  def getTweets(filename: String): Seq[Tweet] = fromJsonFileAs[Seq[Tweet]](filename)

  def top[T](f: Tweet => Seq[T], n: Int)(tweets: Seq[Tweet]): Seq[Record[T]] = {
   val tCount = tweets.flatMap(f).groupBy(identity).mapValues(_.size)
    tCount.toSeq.sortBy { case (t, frequency) => -frequency }.take(n)
  }
  
  def topHashtags(tweets: Seq[Tweet], n: Int = 10) = {
    def extractHashtags: Tweet => Seq[String] = { tweet =>
      tweet.entities.map { entities =>
        entities.hashtags.map(_.text.toLowerCase)
      }.getOrElse(Seq.empty)
    }
    
    val result = top[String](extractHashtags, n)(tweets)
    toPrettyString("TOP HASHTAGS", result) { case (hashtag, frequency) =>
      s"#$hashtag ($frequency times)"
    }
  }

  def topActiveUsers(tweets: Seq[Tweet], n: Int = 10) = {
    def extractUser: Tweet => Seq[String] = { tweet =>
      tweet.user.toSeq.map(_.screen_name)
    }

    val result = top[String](extractUser, n)(tweets)
    toPrettyString("TOP ACTIVE USERS", result) { case (user, frequency) =>
      s"@$user ($frequency tweets)"
    }
  }

  def topMentionedUsers(tweets: Seq[Tweet], n: Int = 10) = {
    def extractMentions: Tweet => Seq[String] = { tweet =>
      tweet.entities.map(_.user_mentions.map(_.screen_name)).getOrElse(Seq.empty)
    }

    val result = top[String](extractMentions, n)(tweets)
    toPrettyString("TOP MENTIONED USERS", result) { case (mention, frequency) =>
      s"@$mention ($frequency times)"
    }
  }

  def topRetweets(tweets: Seq[Tweet], n: Int = 10) = {
    val result = tweets.filterNot(_.text.startsWith("RT ")).sortBy(t => -t.retweet_count).take(n)
    toPrettyString("TOP RETWEETS", result) { tweet =>
      s"${tweet.text.replaceAll("\n"," ")} (by @${tweet.user.map(_.screen_name).getOrElse("unknown")}, retweeted ${tweet.retweet_count} times)"
    }
  }

  def topFavoriteTweets(tweets: Seq[Tweet], n: Int = 10) = {
    val result = tweets.sortBy(t => - t.favorite_count).take(n)
    toPrettyString("TOP FAVORITE TWEETS", result) { tweet =>
      s"${tweet.text.replaceAll("\n"," ")} (by @${tweet.user.map(_.screen_name).getOrElse("unknown")}, liked ${tweet.favorite_count} times)"
    }
  }

  def toPrettyString[T](title: String, data: Seq[T])(f: T => String): String = {
    s"$title\n" +
    data.zipWithIndex.map {case (x, idx) => s"[${idx+1}] ${f(x)}\n"}.mkString
  }

  val filename = {
    val config = ConfigFactory.load()
    config.getString("tweets.scalax")
  }

  val tweets = getTweets(filename)
  println(topHashtags(tweets))
  println(topActiveUsers(tweets))
  println(topMentionedUsers(tweets))
  println(topRetweets(tweets))
  println(topFavoriteTweets(tweets))
}
