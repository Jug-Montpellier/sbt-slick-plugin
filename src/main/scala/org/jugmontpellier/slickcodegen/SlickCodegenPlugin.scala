package org.jugmontpellier.slickcodegen

import java.nio.file.{Files, Paths}

import com.typesafe.config.{Config, ConfigFactory}
import sbt.Keys._
import sbt._

import scala.collection.JavaConverters._
import scala.util.{Failure, Try}

object SlickCodegenPlugin extends AutoPlugin {

  override def trigger = allRequirements

  override def requires: Plugins = plugins.JvmPlugin

  object autoImport {
    val slickCodegenConfFile = settingKey[String]("slick codegen configuration file path")

    val slickCodegen = taskKey[Seq[File]]("Slick codegen")

    val slickOutputDir = settingKey[File]("Output dir")

  }

  import autoImport._

  override lazy val projectSettings = Seq(
    runner := new ForkRun(ForkOptions()),
    slickOutputDir := (scalaSource in Compile).value,

    slickCodegen := {
      val classpath = (dependencyClasspath in Compile).value
      val confFile = slickCodegenConfFile.value
      implicit val logger = streams.value.log

      generates(slickOutputDir.value, classpath, runner.value, confFile, true)
    },
    slickCodegenConfFile := "src/main/slick/slick-codegen.conf",

    sourceGenerators in Compile += Def.task {
      val classpath = (dependencyClasspath in Compile).value
      val confFile = slickCodegenConfFile.value
      implicit val logger = streams.value.log

      generates(slickOutputDir.value, classpath, runner.value, confFile, false)
    }

  )

  private def generates(outputDir: File, classpath: Classpath, run: ScalaRun, confFile: String, force: Boolean)(implicit logger: Logger) = (for {
    config <- confPath(confFile)
    dbs <- databaseNames(config)
  } yield dbs.flatMap {
    db => generate(outputDir, classpath, run, confFile, config, db, force)
  }).getOrElse(Nil).toSeq


  private def databaseNames(config: Config) = Try {
    config.root().entrySet().asScala.map(_.getKey)
  }

  private def generate(outputDir: File, cp: Classpath, runner: ScalaRun, confFile: String, config: Config, conf: String, force: Boolean)(implicit logger: Logger): Seq[File] = for {
    c <- Try(config.getConfig(conf)).toOption.toSeq
  } yield {
    val tables = outputDir / c.getString("codegen.package").replace('.', '/') / "Tables.scala"
    if (force || !tables.exists())
      toError(runner.run("slick.codegen.SourceCodeGenerator", cp.files, Array(s"file:$confFile#$conf", outputDir.getPath), logger))
    tables
  }

  private def confPath(confFile: String)(implicit logger: Logger) = Try(Paths.get(confFile)) map {
    case p if p.isAbsolute => p
    case p => Paths.get(sys.props("user.dir")).resolve(p)
  } flatMap {
    case p if Files.exists(p) => Try(ConfigFactory.parseFile(p.toFile))
    case p =>
      logger.error(s"$p does not exist!")
      Failure(null)
  }


}
