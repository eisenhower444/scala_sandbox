import sbt.Keys._

version := "1.0"

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Chunliang's Maven Repository" at "https://repo.chunlianglyu.com",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
  "org.scalaz" %% "scalaz-core" % "7.2.1",
  "com.github.tototoshi" %% "scala-csv" % "1.2.1",
  "org.typelevel" %% "cats" % "0.6.0",
  "com.chuusai" %% "shapeless" % "2.3.0",
  "com.propensive" %% "rapture" % "2.0.0-M6",
  "joda-time" % "joda-time" % "2.8.1"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.7.0"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4-SNAPSHOT"