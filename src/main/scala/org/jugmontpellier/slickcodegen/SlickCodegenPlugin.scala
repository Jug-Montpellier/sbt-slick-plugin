package org.jugmontpellier.slickcodegen

import java.nio.file.{Files, Paths}

import com.typesafe.config.ConfigFactory
import sbt._
import Keys._

import scala.util.Try

object SlickCodegenPlugin extends AutoPlugin {

  override def requires: Plugins = plugins.JvmPlugin

  object autoImport {
    val slickCodegenConfFile = settingKey[String]("slick codegen configuration file path")
    val slickCodegenConf = settingKey[String]("slick codegen conf ( #thistoken )")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    slickCodegenConfFile := "src/main/slick/slick-codegen.conf",
    runner := new ForkRun(ForkOptions()),
    sourceGenerators in Compile += Def.task[Seq[File]] {
      val dir = (sourceManaged in Compile).value
      val cp = (dependencyClasspath in Compile).value
      val confFile = slickCodegenConfFile.value
      val conf = slickCodegenConf.value
      implicit val logger = streams.value.log
      generate(dir, cp, runner.value, confFile, conf).toSeq
    }.taskValue

  )

  def generate(outputDir: File, cp: Classpath, runner: ScalaRun, confFile: String, conf: String)(implicit logger: Logger): Option[File] = for {
      confPath <- confPath(confFile)
      config <- Try(ConfigFactory.parseFile(confPath.toFile).getConfig(conf)).toOption
    } yield {
      toError(runner.run("slick.codegen.SourceCodeGenerator", cp.files, Array(s"file:$confFile#$conf", outputDir.getPath), logger))
      outputDir / config.getString("codegen.package").replace('.', '/') / "Tables.scala"
    }

  private def confPath(confFile: String)(implicit logger: Logger) = Option(Paths.get(confFile)) map {
    case p if p.isAbsolute => p
    case p => Paths.get(sys.props("user.dir")).resolve(p)
  } flatMap {
    case p if Files.exists(p) => Some(p)
    case p =>
      logger.error(s"$p does not exist!")
      None
  }


}
