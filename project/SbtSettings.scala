import sbt._;
import Keys._


object SbtSettings extends DefaultSbtSettings {

  override def commonProjectSettings: Seq[Setting[_]] = Seq(
    scalacOptions ++= Seq("-Xfatal-warnings", "-deprecation",  "-Xlint:-missing-interpolator", "-deprecation", "-unchecked", "-feature", "-encoding", "utf8", "-language:reflectiveCalls"),
    javacOptions in Compile ++= Seq("-encoding", "utf8", "-g"),
    incOptions := incOptions.value.withNameHashing(true)

  );


  override def commonScalaJsProjectSettings: Seq[Setting[_]] =  {

    Seq()
    /* Uncomment the following lines and remove the above Seq()

     import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
     import playscalajs.PlayScalaJS.autoImport._
     Seq (
       scalacOptions ++= Seq("-Xlint", "-deprecation", "-feature"),
       libraryDependencies += "org.scala-js" %%%! "scalajs-dom" % "0.9.2"
     )
     */
  }


  // overrides the setting for the modules if you need to


}
