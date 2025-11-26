ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19"

lazy val root = (project in file("."))
  .settings(
    name := "Roulette"
  )

coverageEnabled := true
coverageMinimum := 0
coverageFailOnMinimum := false
Test / scalacOptions += "-coverage-out" // required for Scala 3 instrumentation
