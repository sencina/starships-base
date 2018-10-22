name := "starships"

version := "0.1"

scalaVersion := "2.12.3"

mainClass in (Compile, run) := Some("edu.austral.starship.base.Main")
// mainClass in (Compile, run) := Some("edu.austral.starship.scala.base.Main")

// https://mvnrepository.com/artifact/org.processing/core
libraryDependencies += "org.processing" % "core" % "3.2.3"