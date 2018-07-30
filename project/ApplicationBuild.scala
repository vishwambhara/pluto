

import sbt._
import Keys._
import play.sbt.PlayImport._
import PlayKeys._
import com.typesafe.sbt.SbtNativePackager._
import com.typesafe.sbt.SbtNativePackager.autoImport._
import NativePackagerKeys._
import sbt.Project.projectToRef

object ApplicationBuild extends Build {

  val appName = "Pluto"
  val appVersion = "0.1"

  val prepareTaskKey = TaskKey[Unit]("prepare", "Creates the intermediate(xml) file")
  val generateTaskKey = TaskKey[Unit]("generate", "Create code files")


   import com.typesafe.sbt.SbtNativePackager._
   import NativePackagerKeys._

   def filePathMap(f: File) = (f.***) pair relativeTo(f.getParentFile)

   lazy val root = sbt.Project(appName, file("."))
    .enablePlugins(play.sbt.PlayScala
     )
    .settings(libraryDependencies += filters)
    .settings(commands ++= Seq()
         ++ com.iteration3.relax.rsbt.Commands.list
         ++ com.iteration3.relax.rsbt.ComponentCommands.list
         ++ com.iteration3.relax.rsbt.LibCommands.list
         ++ com.iteration3.relax.rsbt.RelaxCommands.list)
    .settings(version := appVersion)
    .settings(onLoad in Global := com.iteration3.relax.rsbt.Commands.initChecks)
    .settings((stage in Universal) :=  {com.iteration3.relax.rsbt.Commands.createSmileStarter(); (stage in Universal).value})
    .settings(bashScriptExtraDefines += """addJava "-Dsmile.staging=true"""")
    .settings(bashScriptConfigLocation := Some((baseDirectory.value / "conf" / "jvmopts").getAbsolutePath))
    .settings(mappings in Universal ++= { Seq() ++ filePathMap(PlutoMongoProject.base / "conf")++ filePathMap(PlutoReadMongoProject.base / "conf")++ filePathMap(PlutoDomainProject.base / "conf")++ filePathMap(PlutoDataProject.base / "conf")++ filePathMap(PlutoApiProject.base / "conf")++ filePathMap(PlutoStreamProject.base / "conf")++ filePathMap(PlutoQueryProject.base / "conf")  
      })
    .settings(com.iteration3.relax.rsbt.RootProjectTasks.taskList(prepareTaskKey, generateTaskKey): _*)
    .settings(hardCodedSettings: _*)
    .settings(SbtSettings.commonProjectSettings: _*)
    .settings(aggregate in update := false)
    .settings(generateTaskKey := (generateTaskKey dependsOn ( generateTaskKey in PlutoMongoProject)).value  ) 
     .settings(generateTaskKey := (generateTaskKey dependsOn ( generateTaskKey in PlutoReadMongoProject)).value  ) 
     .settings(generateTaskKey := (generateTaskKey dependsOn ( generateTaskKey in PlutoDomainProject)).value  ) 
     .settings(generateTaskKey := (generateTaskKey dependsOn ( generateTaskKey in PlutoDataProject)).value  ) 
     .settings(generateTaskKey := (generateTaskKey dependsOn ( generateTaskKey in PlutoApiProject)).value  ) 
     .settings(generateTaskKey := (generateTaskKey dependsOn ( generateTaskKey in PlutoStreamProject)).value  ) 
     .settings(generateTaskKey := (generateTaskKey dependsOn ( generateTaskKey in PlutoQueryProject)).value  ) 
     
    .aggregate(PlutoMongoProject,PlutoReadMongoProject,PlutoDomainProject,PlutoDataProject,PlutoApiProject,PlutoStreamProject,PlutoQueryProject)
    .dependsOn(PlutoMongoProject,PlutoReadMongoProject,PlutoDomainProject,PlutoDataProject,PlutoApiProject,PlutoStreamProject,PlutoQueryProject);


          lazy val PlutoApiProject:sbt.Project = com.iteration3.relax.rsbt.PlayProject("PlutoApi", "SNAPSHOT", file("modules/pluto/PlutoApi"))
              .enablePlugins(play.sbt.PlayScala)
              .settings(com.iteration3.relax.rsbt.StandardServerModuleTasks.taskList("PlutoApi", prepareTaskKey, generateTaskKey): _*)
                    .settings(generateTaskKey := (generateTaskKey dependsOn (generateTaskKey in PlutoDataProject)).value )
                               .settings(generateTaskKey := (generateTaskKey dependsOn (generateTaskKey in PlutoDomainProject)).value )
                               .settings(generateTaskKey := (generateTaskKey dependsOn (generateTaskKey in PlutoQueryProject)).value )
                         .settings(hardCodedSettings: _*)
              .settings(SbtSettings.commonProjectSettings: _*)
              .settings(SbtSettings.PlutoApiProjectSettings:_*)
              .dependsOn(PlutoDataProject,PlutoDomainProject,PlutoQueryProject); 





      lazy val PlutoMongoProject:sbt.Project = com.iteration3.relax.rsbt.Project("PlutoMongo", "SNAPSHOT")
          .settings(com.iteration3.relax.rsbt.StandardServerModuleTasks.taskList("PlutoMongo", prepareTaskKey, generateTaskKey): _*)
          .settings(com.iteration3.relax.rsbt.MongoTasks.taskList("PlutoMongo", prepareTaskKey, generateTaskKey): _*)
          .settings(hardCodedSettings: _*)
          .settings(SbtSettings.commonProjectSettings: _*)
          .settings(SbtSettings.PlutoMongoProjectSettings:_*)
          .dependsOn(); 
      lazy val PlutoReadMongoProject:sbt.Project = com.iteration3.relax.rsbt.Project("PlutoReadMongo", "SNAPSHOT")
          .settings(com.iteration3.relax.rsbt.StandardServerModuleTasks.taskList("PlutoReadMongo", prepareTaskKey, generateTaskKey): _*)
          .settings(com.iteration3.relax.rsbt.MongoTasks.taskList("PlutoReadMongo", prepareTaskKey, generateTaskKey): _*)
          .settings(hardCodedSettings: _*)
          .settings(SbtSettings.commonProjectSettings: _*)
          .settings(SbtSettings.PlutoReadMongoProjectSettings:_*)
          .dependsOn(); 
      lazy val PlutoDomainProject:sbt.Project = com.iteration3.relax.rsbt.Project("PlutoDomain", "SNAPSHOT")
          .settings(com.iteration3.relax.rsbt.StandardServerModuleTasks.taskList("PlutoDomain", prepareTaskKey, generateTaskKey): _*)
                    .settings(generateTaskKey := (generateTaskKey dependsOn (generateTaskKey in PlutoMongoProject)).value )
                             .settings(generateTaskKey := (generateTaskKey dependsOn (generateTaskKey in PlutoStreamProject)).value )
                    
          .settings(com.iteration3.relax.rsbt.DomainTasks.taskList("PlutoDomain", prepareTaskKey, generateTaskKey): _*)
          .settings(hardCodedSettings: _*)
          .settings(SbtSettings.commonProjectSettings: _*)
          .settings(SbtSettings.PlutoDomainProjectSettings:_*)
          .dependsOn(PlutoMongoProject,PlutoStreamProject); 
      lazy val PlutoDataProject:sbt.Project = com.iteration3.relax.rsbt.Project("PlutoData", "SNAPSHOT")
          .settings(com.iteration3.relax.rsbt.StandardServerModuleTasks.taskList("PlutoData", prepareTaskKey, generateTaskKey): _*)
          .settings(com.iteration3.relax.rsbt.DataTasks.taskList("PlutoData", prepareTaskKey, generateTaskKey): _*)
          .settings(hardCodedSettings: _*)
          .settings(SbtSettings.commonProjectSettings: _*)
          .settings(SbtSettings.PlutoDataProjectSettings:_*)
          .dependsOn(); 
      lazy val PlutoStreamProject:sbt.Project = com.iteration3.relax.rsbt.Project("PlutoStream", "SNAPSHOT")
          .settings(com.iteration3.relax.rsbt.StandardServerModuleTasks.taskList("PlutoStream", prepareTaskKey, generateTaskKey): _*)
          .settings(com.iteration3.relax.rsbt.StreamTasks.taskList("PlutoStream", prepareTaskKey, generateTaskKey): _*)
          .settings(hardCodedSettings: _*)
          .settings(SbtSettings.commonProjectSettings: _*)
          .settings(SbtSettings.PlutoStreamProjectSettings:_*)
          .dependsOn(); 
      lazy val PlutoQueryProject:sbt.Project = com.iteration3.relax.rsbt.Project("PlutoQuery", "SNAPSHOT")
          .settings(com.iteration3.relax.rsbt.StandardServerModuleTasks.taskList("PlutoQuery", prepareTaskKey, generateTaskKey): _*)
                    .settings(generateTaskKey := (generateTaskKey dependsOn (generateTaskKey in PlutoReadMongoProject)).value )
                             .settings(generateTaskKey := (generateTaskKey dependsOn (generateTaskKey in PlutoStreamProject)).value )
                    
          .settings(com.iteration3.relax.rsbt.QueryTasks.taskList("PlutoQuery", prepareTaskKey, generateTaskKey): _*)
          .settings(hardCodedSettings: _*)
          .settings(SbtSettings.commonProjectSettings: _*)
          .settings(SbtSettings.PlutoQueryProjectSettings:_*)
          .dependsOn(PlutoReadMongoProject,PlutoStreamProject); 









    def commonCommands =
     Seq(
       commands ++= Seq(
         com.iteration3.relax.rsbt.Commands.clearConsoleCommand,
         com.iteration3.relax.rsbt.Commands.cmdCommand,
         com.iteration3.relax.rsbt.Commands.addSwaggerCommand,
         com.iteration3.relax.rsbt.Commands.removeSwaggerCommand,
         com.iteration3.relax.rsbt.Commands.smileUpdateCommand)
     )

   def noDocGen = Seq(
     publishArtifact in(Compile, packageSrc) := false,
     publishArtifact in packageSrc := false,
     publishArtifact in(Compile, packageDoc) := false,
     publishArtifact in packageDoc := false,
     sources in(Compile, doc) := Seq.empty
   )

   def hardCodedSettings = noDocGen ++ commonCommands ++ hardCodedLibrarySettings ++ unmanagedJarSettings ++ artifactNameSettings

  def hardCodedLibrarySettings = libraryDependencies ++= hardCodedCommonDependencies ++ importLibDependencies


  def artifactNameSettings = artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
     val classifierStr = artifact.classifier match {
        case None => "";
        case Some(c) => "-" + c
      }
      artifact.name + "-" + module.revision + classifierStr + "." + artifact.extension
    }

  def unmanagedJarSettings = unmanagedJars in Compile ++= {
    val mayBeRelaxRelaxHome = Option(System.getProperty("SMILE_HOME"));
    val relaxHome = file(mayBeRelaxRelaxHome.get);
    val dirs = (relaxHome / "lib" / "run-lib") +++ (relaxHome / "dist" / "run-dist")
    (dirs ** "*.jar").classpath
  }

  lazy val hardCodedCommonDependencies = Seq(

           "org.fusesource.jansi" % "jansi" % "1.4",

           "org.slf4j" % "slf4j-api" % "1.7.6",
           "org.slf4j" % "jul-to-slf4j" % "1.7.6",
           "org.slf4j" % "jcl-over-slf4j" % "1.7.6",

           "ch.qos.logback" % "logback-core" % "1.1.7",
           "ch.qos.logback" % "logback-classic" % "1.1.7",

           "com.typesafe.slick" % "slick_2.11" % "2.1.0",
           "com.typesafe.play" %% "play" % "2.4.11",

           "mysql" % "mysql-connector-java" % "6.0.4",
           "com.jolbox" % "bonecp" % "0.8.0.RELEASE",
           "org.scalikejdbc" %% "scalikejdbc" % "2.4.2",
           "com.google.inject" % "guice" % "4.0",

           "com.typesafe.akka" %% "akka-actor" % "2.3.8",
           "com.typesafe.akka" %% "akka-slf4j" % "2.3.8",
           "com.typesafe.akka" %% "akka-remote" % "2.3.8",
           "com.typesafe.akka" %% "akka-testkit" % "2.3.8",
         //  "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.8",

           "com.rabbitmq" % "amqp-client" % "4.1.0",
           "org.mongodb" % "mongo-java-driver" % "2.14.2",
           "org.scalatest" % "scalatest_2.11" % "2.2.0",
           "org.scalacheck" %% "scalacheck" % "1.10.1",
           "junit" % "junit" % "4.10" % "test",
           "org.pegdown" % "pegdown" % "1.1.0" % "test",
           "org.apache.httpcomponents" % "httpclient" % "4.5.2"

  )

  lazy val importLibDependencies = Seq(
  )

}


trait DefaultSbtSettings {

  def commonProjectSettings: Seq[Setting[_]] = Seq();
  def commonScalaJsProjectSettings: Seq[Setting[_]] = Seq();

  def PlutoMongoProjectSettings:Seq[Setting[_]] = Seq(); 
  def PlutoReadMongoProjectSettings:Seq[Setting[_]] = Seq(); 
  def PlutoDomainProjectSettings:Seq[Setting[_]] = Seq(); 
  def PlutoDataProjectSettings:Seq[Setting[_]] = Seq(); 
  def PlutoApiProjectSettings:Seq[Setting[_]] = Seq(); 
  def PlutoStreamProjectSettings:Seq[Setting[_]] = Seq(); 
  def PlutoQueryProjectSettings:Seq[Setting[_]] = Seq(); 


}
