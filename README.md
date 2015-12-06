# twitter4s-demo
Examples on how to use [twitter4s](https://github.com/DanielaSfregola/twitter4s) library.

Usage
-----
Change the `application.conf` file with your consumer and access tokens (see [twitter4s README](https://github.com/DanielaSfregola/twitter4s#prerequisites) for more information):
```scala
twitter { // TODO - change these configs! see https://github.com/DanielaSfregola/twitter4s#prerequisites
  consumer {
    key = "my-consumer-key"
    secret = "my-consumer-secret"
  }

  access {
    key = "my-access-key"
    secret = "my-access-secret"
  }
}
```

Run the examples with `sbt run` and choose the main to run.

Examples
--------
- [MyTopHashtags](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/MyTopHashtags.scala) displays the top 10 most common hashtags in the home timeline (i.e.: the timeline of the authenticated user).
- [UserTopHashtags](https://github.com/DanielaSfregola/twitter4s-demo/blob/master/src/main/scala/UserTopHashtags.scala) displays the top 10 most common hashtags in a user timeline. In this example, we use Martin Odersky's account.
