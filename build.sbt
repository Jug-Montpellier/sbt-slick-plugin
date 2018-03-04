import sbtrelease.ReleaseStateTransformations._

name := "sbt-slick-plugin"

organization := "io.metabookmarks"

crossSbtVersions := Vector("0.13.16", "1.1.1")

releaseCrossBuild := true

sbtPlugin := true

libraryDependencies += "com.typesafe" % "config" % "1.3.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.25" % Test
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
