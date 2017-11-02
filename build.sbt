name := "sbt-autojump"

organization := "xyz.luisfonseca"

version := "1.0.1"

crossSbtVersions := Vector("0.13.16", "1.0.2")

licenses := Seq("MIT License" -> url("https://raw.githubusercontent.com/luismfonseca/sbt-autojump/master/LICENSE"))

sbtPlugin := true

// Scripted - sbt plugin tests
scriptedLaunchOpts += s"-Dplugin.version=${version.value}"
scriptedBufferLog := false

test := ScriptedPlugin.scriptedTask.toTask("").value

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
