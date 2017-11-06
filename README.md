# twitter4s-demo
[![Build Status](https://travis-ci.org/DanielaSfregola/twitter4s-demo.svg?branch=master)](https://travis-ci.org/DanielaSfregola/twitter4s-demo) [![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt) [![Chat](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/twitter4s/Lobby)


Examples on how to use [twitter4s](https://github.com/DanielaSfregola/twitter4s) library.

Usage
-----
Make sure to set up your consumer and access tokens -- see [twitter4s README - Usage Section](https://github.com/DanielaSfregola/twitter4s#usage) for more information.

Run the examples with `sbt run` and choose the main to run.

TwitterRestClient Examples
--------------------------------
- [MyTopHashtags](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/MyTopHashtags.scala) displays the top 10 most common hashtags in the home timeline (i.e.: the timeline of the authenticated user).
- [SearchAndSaveTweets](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/SearchAndSaveTweets.scala) searches all the tweets matching some query and it saves the result as JSON in a file. In this example, the query used is `#scalax` and the generated file is `src/main/resources/tweets/scalax.json`.
- [SimpleStatistics](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/SimpleStatistics.scala) loads tweets from a file, in this case `src/main/resources/tweets/scalax.json`, and it computes some simple statistics: top hashtags, top active users, top mentioned users, top retweets, top favorite tweets.
- [UserTopHashtags](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/UserTopHashtags.scala) displays the top 10 most common hashtags in a user timeline. In this example, we use Martin Odersky's account.
- [TrendingTopics](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/TrendingTopics.scala) displays the top 10 trending topics worldwide, and in a given location (by text and by geolocation).
- [LastTweetInTimeline](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/LastTweetInTimeline.scala) displays the last tweet in your timeline.
- [FollowersRegisteredTheSameYear](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/FollowersRegisteredTheSameYear.scala) display for a user, the followers registered the same year.

TwitterStreamingClient Examples
--------------------------------
- [UserMentionStream](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/streaming/UserMentionStream.scala) receive stream of tweets mentioning yourself.
- [StreamingWithLogging](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/streaming/StreamingWithLogging.scala) example on how to use logging in your twitter4s application.

Troubleshooting
-----------------

If you run the examples, and nothing happens, use the `scala.util.Try` type to debug. Here is an example:

````scala
  client.homeTimeline(count = 2) onComplete {
    case Success(_) => println("all good ")
    case Failure(ex) => println(ex)
    }
````

If you have this exception `javax.net.ssl.SSLHandshakeException: General SSLEngine problem` add the certificate of the twitter api to jre truststore. 

```bash
echo -n | \
openssl s_client -connect api.twitter.com:443 </dev/null | \
sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > /tmp/twitter-api.crt && \
sudo keytool -import -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -alias twitterapi -file /tmp/twitter-api.crt
```

if the alias already exists, delete it before.
```bash
sudo keytool -delete -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -alias twitterapi
```

Bugs
----
Spotted some bugs? Please, raise them in the [twitter4s](https://github.com/DanielaSfregola/twitter4s/issues) issue section.
