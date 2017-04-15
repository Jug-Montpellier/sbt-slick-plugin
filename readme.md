#sbt-slick-plugin

##Usage

Declate plugin in project/plugin.sbt:

`sbt
addSbtPlugin("org.jug-montpellier" % "sbt-slick-plugin" % "0.0.4")
`

Write codegen descriptor in: src/main/slick/slick-codegen.conf

`
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
`

That's it ...

Codegen will run:

*implictly on compile, only once unless clean is performed
*explictly with task `slickCodegen`


scala code is generated in `target/scala-2.12/src_managed/main/`

Enjoy.


