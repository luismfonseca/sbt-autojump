import sbt._
import Keys._

object TestProjectBuild extends Build {
  lazy val root = Project(id = "root", base = file("."))
  lazy val footprint = Project(id = "footprint", base = file("footprint"))
  lazy val bar = Project(id = "bar", base = file("bar"))

  val isCurrentProject = InputKey[Unit]("isCurrentProject", "Validates if current project is the given input")

  override lazy val settings = super.settings ++ Seq(
    isCurrentProject in ThisBuild <<= inputTask { (argTask: TaskKey[Seq[String]]) =>
      (argTask, state) map { (arg, s) =>
        val currentProject = Project.extract(s).currentProject.id
        if (arg.head == currentProject) {
          s.log.success(s"Yes, it's project $currentProject.")
        } else {
          sys.error(s"No, it's not project $currentProject.")
        }
      }
    }
  )
}
