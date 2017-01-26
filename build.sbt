

val coreSettings = Seq(
  sonatypeProfileName := "com.github.j5ik2o",
  organization := "com.github.j5ik2o",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-feature"
    , "-deprecation"
    , "-unchecked"
    , "-encoding"
    , "UTF-8"
    , "-Xfatal-warnings"
    , "-language:existentials"
    , "-language:implicitConversions"
    , "-language:postfixOps"
    , "-language:higherKinds"
    , "-Yinline-warnings" // Emit inlining warnings. (Normally surpressed due to high volume)
    , "-Ywarn-adapted-args" // Warn if an argument list is modified to match the receiver
    , "-Ywarn-dead-code" // Warn when dead code is identified.
    , "-Ywarn-inaccessible" // Warn about inaccessible types in method signatures.
    , "-Ywarn-infer-any" // Warn when a type argument is inferred to be `Any`.
    , "-Ywarn-nullary-override" // Warn when non-nullary `def f()' overrides nullary `def f'
    , "-Ywarn-nullary-unit" // Warn when nullary methods return Unit.
    , "-Ywarn-numeric-widen" // Warn when numerics are widened.
    , "-Ywarn-unused" // Warn when local and private vals, vars, defs, and types are are unused.
    , "-Xmax-classfile-name", "200"
  ),
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
        <url>git@github.com:j5ik2o/scala-product-advertising-api.git</url>
        <connection>scm:git:github.com/j5ik2o/scala-product-advertising-api-api</connection>
        <developerConnection>scm:git:git@github.com:j5ik2o/scala-product-advertising-api.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>j5ik2o</id>
          <name>Junichi Kato</name>
        </developer>
      </developers>
  },
  credentials := Def.task {
    val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
    val result = Credentials(ivyCredentials) :: Nil
    result
  }.value
) ++ scalariformSettings

val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
val scalaParser = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5"
//val dispatchVersion = "0.12.0"
//val dipatchCore = "net.databinder.dispatch" %% "dispatch-core" % dispatchVersion

val stub = (project in file("stub"))
  .enablePlugins(ScalaxbPlugin)
  .settings(coreSettings)
  .settings(
    name := "scala-product-advertising-api-model",
    libraryDependencies ++= {
      if (scalaVersion.value startsWith "2.10") Seq()
      else Seq(scalaXml, scalaParser)
    },
//    libraryDependencies ++= Seq(
//      dipatchCore
//    ),
//    scalaxbDispatchVersion in(Compile, scalaxb) := dispatchVersion,
    scalaxbPackageDir in(Compile, scalaxb) := true,
    scalaxbPackageName in(Compile, scalaxb) := "apa",
    scalaxbAutoPackages in(Compile, scalaxb) := true,
    scalaxbGenerateDispatchClient in(Compile, scalaxb) := false
//    logLevel in(Compile, scalaxb) := Level.Debug
  )

val api = (project in file("api"))
  .settings(coreSettings)
  .settings(
    name := "scala-product-advertising-api-core",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "com.typesafe.akka" %% "akka-http-testkit" % "10.0.2" % Test,
      "commons-codec" % "commons-codec" % "1.10",
      "com.typesafe.akka" %% "akka-http-core" % "10.0.2",
      "com.typesafe.akka" %% "akka-http" % "10.0.2",
      "com.typesafe.akka" %% "akka-http-xml" % "10.0.2",
      "com.chuusai" %% "shapeless" % "2.3.2"
    )
  ).dependsOn(stub)

val root = (project in file("."))
  .settings(
    name := "scala-product-advertising-api-project"
  ).settings(coreSettings)
  .aggregate(api, stub)

