name := "sbt-autojump"

organization := "xyz.luisfonseca"

version := "1.0.0"

scalaVersion := "2.10.6"

sbtPlugin := true

// Scripted - sbt plugin tests
scriptedSettings
scriptedLaunchOpts <+= version apply { v => "-Dproject.version="+v }

test <<= scriptedTask.toTask("")
