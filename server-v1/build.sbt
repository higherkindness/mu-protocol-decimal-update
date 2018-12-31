import higherkindness.mu.rpc.idlgen.Model._

name := "decimal-migration-guide-server"

version := "1"

organization := "io.higherkindness"

scalaVersion := "2.12.7"

resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

idlType := "avro"

srcGenSerializationType := "Avro"

sourceGenerators in Compile += (srcGen in Compile).taskValue

srcGenJarNames := Seq("decimal-migration-guide-protocol")

idlGenBigDecimal := ScalaBigDecimalGen

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "io.higherkindness" %% "mu-rpc-server" % "0.17.0",
  "io.higherkindness" %% "mu-rpc-channel" % "0.17.0",
  "io.higherkindness" % "decimal-migration-guide-protocol" % "1"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.patch)
