import complete.DefaultParsers._

lazy val footprint = project in file("footprint")
lazy val bar = project in file("bar")

val isCurrentProject = inputKey[Unit]("Validates if current project is the given input")

lazy val root = (project in file("."))
  .settings(
    isCurrentProject in ThisBuild := {
      val st = state.value
      val args: Seq[String] = spaceDelimited("<arg>").parsed

      val currentProject = Project.extract(st).currentProject.id
      if (args.head == currentProject) {
        st.log.success(s"Yes, it's project $currentProject.")
      } else {
        sys.error(s"No, it's not project $currentProject.")
      }
    })
