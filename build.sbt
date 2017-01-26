name := "scala-product-advertising-api"

val coreSettings = Seq(
  sonatypeProfileName := "com.github.j5ik2o",
  organization := "com.github.j5ik2o",
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.12.1",
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := {
    _ => false
  },
  pomExtra := {
    <url>https://github.com/j5ik2o/scala-product-adverting-api</url>
      <licenses>
        <license>
          <name>The MIT License</name>
          <url>http://opensource.org/licenses/MIT</url>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:j5ik2o/scala-product-adverting-api.git</url>
        <connection>scm:git:github.com/j5ik2o/scala-product-adverting-api</connection>
        <developerConnection>scm:git:git@github.com:j5ik2o/scala-adverting-api.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>j5ik2o</id>
          <name>Junichi Kato</name>
        </developer>
      </developers>
  },
  credentials <<= Def.task {
    val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
    val result = Credentials(ivyCredentials) :: Nil
    result
  }
)

val root = (project in file(".")).settings(coreSettings)

val stub = (project in file("stub"))
  .enablePlugins(ScalaxbPlugin)
  .settings(coreSettings)
  .settings(
    name := "stub",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5",
      "net.databinder.dispatch" %% "dispatch-core" % "0.12.0"
    ),
    scalaxbPackageName in(Compile, scalaxb) := "apa",
    scalaxbAutoPackages in(Compile, scalaxb) := true
  )

val api = (project in file("api"))
  .settings(coreSettings).settings(
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % "2.12.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.2" % Test,
    "commons-codec" % "commons-codec" % "1.10",
    "com.typesafe.akka" %% "akka-http-core" % "10.0.2",
    "com.typesafe.akka" %% "akka-http" % "10.0.2",
    "com.typesafe.akka" %% "akka-http-xml" % "10.0.2",
    "com.chuusai" %% "shapeless" % "2.3.2"
  )
).dependsOn(stub)

