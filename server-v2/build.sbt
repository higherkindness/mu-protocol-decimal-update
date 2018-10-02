import freestyle.rpc.idlgen.Model.CustomMarshallersImport

name := "decimal-migration-guide-service"

version := "1"

organization := "io.free"

scalaVersion := "2.12.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("beyondthelines", "maven")
)

idlType := "avro"

srcGenSerializationType := "Avro"

sourceGenerators in Compile += (srcGen in Compile).taskValue

srcGenJarNames := Seq("legacy-avro-decimal-compat-protocol", "decimal-migration-guide-protocol")

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "io.frees" %% "frees-rpc-server" % "0.15.1-FEDE",
  "io.frees" %% "frees-rpc-client-core" % "0.15.1-FEDE",
  "io.frees" %% "legacy-avro-decimal-compat-encoders" % "0.15.1-FEDE" exclude("io.frees", "legacy-avro-decimal-compat-model"),
  "io.frees" % "legacy-avro-decimal-compat-protocol" % "0.15.1-FEDE",
  "io.frees" % "decimal-migration-guide-protocol" % "2"
)

idlGenBigDecimal := freestyle.rpc.idlgen.Model.ScalaBigDecimalTaggedGen

idlGenMarshallerImports += CustomMarshallersImport("freestyle.rpc.protocols.legacyAvroDecimalEncoders._")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.patch)

//updateOptions := updateOptions.value.withLatestSnapshots(false)