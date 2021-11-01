name := "twitter4s-demo"

version := "0.1"

scalaVersion := "2.13.7"

resolvers += Resolver.sonatypeRepo("releases")

scalacOptions in ThisBuild ++= Seq("-language:postfixOps",
                                   "-language:implicitConversions",
                                   "-language:existentials",
                                   "-feature",
                                   "-deprecation",
                                   "-encoding",
                                   "UTF-8",
                                   "-Xlint",
                                   "-Xfatal-warnings",
                                   "-unchecked")


scalafmtTestOnCompile := true
scalafmtShowDiff in scalafmt := true

libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s"      % "7.0",
  "ch.qos.logback"      % "logback-classic" % "1.2.3"
)


