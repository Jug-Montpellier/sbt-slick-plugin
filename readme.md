# sbt-slick-plugin

## Usage

Declate plugin in project/plugin.sbt:

```sbtshell
resolvers += Resolver.bintrayIvyRepo("metabookmarks", "sbt-plugin-releases")
addSbtPlugin("org.jug-montpellier" % "sbt-slick-plugin" % "0.1.1")
```

Write codegen descriptor in: src/main/slick/slick-codegen.conf

```hocon
slickdemo {
  profile = "slick.jdbc.PostgresProfile$"
  codegen = {
    package = "slickdemo.model"
  }
  db = {
    url = "jdbc:postgresql:slickdemo"
    name="slickdemo"
  }
}
```

That's it ...

Codegen will run:

* implictly on compile, if Table.scala does not exist.
* explictly (and forces override) with task
```sbtshell
slickCodegen
```

scala code will be generated in ```src/main/scala```

Use setting __slickOutputDir__:
```sbtshell
slickOutputDir := (sourceManaged in Compile).value
```
and scala code will be generated in ```target/scala-2.12/src_managed/main/```

To tie codegeneration to compile:
```sbtshell
sourceGenerators in Compile += SlickCodegenPlugin.sourcesGenerator
```

Enjoy.


