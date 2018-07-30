unmanagedJars in Compile ++= {
  val mayBeRelaxRelaxHome = Option(System.getProperty("SMILE_HOME"))

  val relaxHome = file(mayBeRelaxRelaxHome.get)
  val dirs = (relaxHome / "lib" / "dev-lib") +++ (relaxHome / "dist" / "dev-dist")
  (dirs ** "*.jar").classpath
}

libraryDependencies in ThisBuild += "com.typesafe.slick" % "slick_2.10" % "2.1.0"

libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.4"

libraryDependencies += "org.antlr" % "stringtemplate" % "3.2.1"

libraryDependencies += "org.asciidoctor" % "asciidoctorj" % "1.5.0"

