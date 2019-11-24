import sbtrelease.ReleaseStateTransformations._

name := "sbt-slick-plugin"

organization := "io.metabookmarks"

crossSbtVersions := Vector("0.13.17", "1.3.4")

releaseCrossBuild := true

sbtPlugin := true

libraryDependencies += "com.typesafe" % "config" % "1.4.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.29" % Test
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

publishMavenStyle := false

bintrayOrganization := Some("metabookmarks")

bintrayRepository := "sbt-plugin-releases"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("^ test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("^ publish"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
