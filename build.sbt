name := "twitter4s-demo"

version := "0.1"

scalaVersion := "2.12.1"

resolvers += Resolver.sonatypeRepo("releases")

scalacOptions in ThisBuild ++= Seq("-language:postfixOps",
                                   "-language:implicitConversions",
                                   "-language:existentials",
                                   "-feature",
                                   "-deprecation")

scalafmtTestOnCompile := true  
scalafmtShowDiff in scalafmt := true 

libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s"      % "5.3",
  "ch.qos.logback"      % "logback-classic" % "1.1.9"
)


