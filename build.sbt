import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

scalaVersion in ThisBuild := "2.11.11"

resolvers in ThisBuild += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers in ThisBuild += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

maxErrors in ThisBuild := 10

shellPrompt in ThisBuild := { s => "[" + Project.extract(s).currentProject.id + "] "}

parallelExecution in Test in Global := false

updateOptions in ThisBuild := updateOptions.value.withCachedResolution(true)

// testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-h", (target.value / "test-report").getAbsolutePath)
