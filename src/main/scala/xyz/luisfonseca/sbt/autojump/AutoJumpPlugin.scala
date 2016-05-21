package xyz.luisfonseca.sbt.autojump

import sbt._

/**
  * This plugin allows the user to switch between existing projects in a similar fashion as autojump.
  */
object AutoJumpPlugin extends AutoPlugin {

  final val commandName = "j"
  final val commandHelpDescription = "Automatically attempts to jump to a project passed as an argument."
  lazy val help =
    Help(
      briefHelp = (commandName, s"<project name: partial or fuzzy>\t$commandHelpDescription"),
      detailedHelp = Map(commandName -> commandHelpDescription))

  lazy val autoj = Command.single(commandName, help) { (state: State, _arg: String) =>
    val arg = _arg.filter(_ != ' ')

    val Some((structure, session, currentRef)) = Extracted.unapply(Project.extract(state))

    val allProjects = structure.allProjectRefs.sorted
    val allProjectsName = allProjects.map(_.project)

    // 1. Simple String.Contains strategy
    val containsMatch = structure.allProjectRefs
      .sorted
      .filterNot(_.project == currentRef.project)
      .find(_.project.contains(arg))

    // 2. Sort all projects by the name distance to `arg` (Levenshtein algorithm), then select the one that
    // has all the chars in `arg` by their order present.
    val distanceSortedProjects =
      Command.suggestions(arg, allProjectsName, allProjectsName.map(_.length).max, structure.allProjectRefs.length)
        .filterNot(_ == currentRef.project)

    val containsDistanceMatch = {
      def containsInOrder(a: String, b: String): Boolean =
        b.zipWithIndex.foldLeft(0) {
          case (-1, _) => -1
          case (cursor, (letter, index)) => a.indexOf(letter, cursor)
        } != -1

      distanceSortedProjects.find(p => containsInOrder(p, arg))
        .map(project => structure.allProjectRefs.find(_.project == project).get)
    }

    containsMatch.orElse(containsDistanceMatch) match {
      case None =>
        state.log.error("No valid jump found for: " + arg)
        state.fail
      case Some(projectRef) =>
        val newSession = session.setCurrent(projectRef.build, projectRef.project, session.currentEval)

        Project.setProject(newSession, structure, state)
    }
  }

  override def projectSettings: Seq[Setting[_]] = Seq(sbt.Keys.commands += autoj)

  override def trigger: PluginTrigger = allRequirements
}
