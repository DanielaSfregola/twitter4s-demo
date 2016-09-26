# twitter4s-demo
Examples on how to use [twitter4s](https://github.com/DanielaSfregola/twitter4s) library.

Usage
-----
Make sure to set up your consumer and access tokens -- see [twitter4s README - Usage Section](https://github.com/DanielaSfregola/twitter4s#usage) for more information.

Run the examples with `sbt run` and choose the main to run.

Examples using TwitterRestClient
--------------------------------
- [MyTopHashtags](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/MyTopHashtags.scala) displays the top 10 most common hashtags in the home timeline (i.e.: the timeline of the authenticated user).
- [SearchAndSaveTweets](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/SearchAndSaveTweets.scala) searches all the tweets matching some query and it saves the result as JSON in a file. In this example, the query used is `#scalax` and the generated file is `src/main/resources/tweets/scalax.json`.
- [SimpleStatistics](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/SimpleStatistics.scala) loads tweets from a file, in this case `src/main/resources/tweets/scalax.json`, and it computes some simple statistics: top hashtags, top active users, top mentioned users, top retweets, top favorite tweets.
- [UserTopHashtags](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/rest/UserTopHashtags.scala) displays the top 10 most common hashtags in a user timeline. In this example, we use Martin Odersky's account.
