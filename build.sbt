import sbtrelease.ReleaseStateTransformations._

name := "sbt-slick-plugin"

organization := "io.metabookmarks"

crossSbtVersions := Vector("0.13.16", "1.0.0")

releaseCrossBuild := true

resolvers += Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

sbtPlugin := true

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % Test
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
