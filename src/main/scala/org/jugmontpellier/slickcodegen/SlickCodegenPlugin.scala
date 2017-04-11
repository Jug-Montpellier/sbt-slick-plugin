package org.jugmontpellier.slickcodegen

import sbt._
import Keys._

object SlickCodegenPlugin extends AutoPlugin {

  override def requires: Plugins = plugins.JvmPlugin

  override lazy val projectSettings = Seq(
    sourceGenerators in Compile += Def.task[Seq[File]] {
      generate( sourceManaged.value,
        (dependencyClasspath in Compile).value, runner.value, streams.value
      )}.taskValue
      //(sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { case (dir, cp, r, s) => generate(dir)
      
  )

  def generate(dir: File,cp: Classpath, run: ScalaRun, s: TaskStreams) : Seq[File] = {
    val outputDir = dir // place generated files in sbt's managed sources folder
    val conf = "file:src/main/slick/slick-codegen.conf#kafka"
    toError(run.run("slick.codegen.SourceCodeGenerator", cp.files, Array(conf, outputDir.getPath), s.log))
    Seq(dir / "kafka" / "model" / "Tables.scala")
  }

}
