name := "decimal-migration-guide-service"

version := "3"

organization := "io.free"

scalaVersion := "2.12.7"

resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

idlType := "avro"

srcGenSerializationType := "Avro"

sourceGenerators in Compile += (srcGen in Compile).taskValue

srcGenJarNames := Seq("legacy-avro-decimal-compat-protocol", "decimal-migration-guide-protocol")

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "io.frees" %% "frees-rpc-server" % "0.15.1",
  "io.frees" %% "frees-rpc-client-core" % "0.15.1",
  "io.frees" % "decimal-migration-guide-protocol" % "3"
)

idlGenBigDecimal := freestyle.rpc.idlgen.Model.ScalaBigDecimalTaggedGen

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.patch)
