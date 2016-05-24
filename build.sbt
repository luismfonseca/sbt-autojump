name := "sbt-autojump"

organization := "xyz.luisfonseca"

version := "1.0.1"

scalaVersion := "2.10.6"

licenses := Seq("MIT License" -> url("https://raw.githubusercontent.com/luismfonseca/sbt-autojump/master/LICENSE"))

sbtPlugin := true

// Scripted - sbt plugin tests
scriptedSettings
scriptedLaunchOpts <+= version apply { v => "-Dproject.version="+v }

test <<= scriptedTask.toTask("")

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

pomExtra := {
  <url>https://github.com/luismfonseca/sbt-autojump</url>
    <scm>
      <connection>scm:git:github.com/luismfonseca/sbt-autojump.git</connection>
      <developerConnection>scm:git:git@github.com:luismfonseca/sbt-autojump.git</developerConnection>
      <url>github.com/luismfonseca/sbt-autojump.git</url>
    </scm>
    <developers>
      <developer>
        <id>luismfonseca</id>
        <name>Luís Fonseca</name>
        <url>www.luisfonseca.xyz</url>
      </developer>
    </developers>
}
