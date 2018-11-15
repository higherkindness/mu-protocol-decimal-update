name := "decimal-migration-guide-protocol"

version := "1"

organization := "io.frees"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "io.frees" %% "frees-rpc-server" % "0.13.7",
  "io.frees" %% "frees-rpc-client-core" % "0.13.7"
)

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.full)

libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % Provided

scalacOptions += "-Xplugin-require:macroparadise"

scalacOptions in (Compile, console) ~= (_ filterNot (_ contains "paradise"))

sources in (Compile,doc) := Seq.empty