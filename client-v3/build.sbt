name := "decimal-migration-guide-client"

version := "3"

organization := "io.higherkindness"

scalaVersion := "2.12.7"

resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

idlType := "avro"

srcGenSerializationType := "Avro"

sourceGenerators in Compile += (srcGen in Compile).taskValue

srcGenJarNames := Seq("decimal-migration-guide-protocol")

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "io.higherkindness" %% "mu-rpc-netty" % "0.17.0",
  "io.higherkindness" % "decimal-migration-guide-protocol" % "3"
)

idlGenBigDecimal := higherkindness.mu.rpc.idlgen.Model.ScalaBigDecimalTaggedGen

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.patch)
