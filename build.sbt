name := "sbt-slick-plugin"

organization := "org.jug-montpellier"

resolvers += Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

sbtPlugin := true

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.25" % Test
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

publishMavenStyle := false

bintrayOrganization := Some("jug-montpellier")

bintrayRepository := "sbt-plugin-releases"
