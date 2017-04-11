name := "sbt-slick-plugin"

organization := "org.jug-montpellier"

resolvers += Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

sbtPlugin := true

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1"

publishMavenStyle := false

bintrayOrganization := Some("jug-montpellier")

bintrayRepository := "sbt-plugin-releases"
