scalaVersion:= "2.11.7"

scalacOptions += "-deprecation"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.11" % "test->default",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)