resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

logLevel := Level.Warn

libraryDependencies += "org.scala-sbt" % "scripted-plugin" % sbtVersion.value

// Scripted plugin needs to declare this as a dependency
libraryDependencies += "jline" % "jline" % "2.11"

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.2")
