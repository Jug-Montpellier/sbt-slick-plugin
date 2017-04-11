name := "sbt-slick-plugin"

organization := "org.jug-montpellier"

resolvers += Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

//scalaVersion := "2.12.1"
//scalaVersion := "2.11.8"

sbtPlugin := true

val slickVersion = "3.2.0"

//libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % slickVersion

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1"

publishMavenStyle := false

bintrayOrganization := Some("jug-montpellier")

bintrayRepository := "sbt-plugin-releases"
