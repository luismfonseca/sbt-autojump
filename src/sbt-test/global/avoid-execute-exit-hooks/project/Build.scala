import sbt._
import Keys._

object TestProjectBuild extends Build {
  lazy val root = Project(id = "root", base = file("."))
  lazy val footprint = Project(id = "foo", base = file("foo"))
  lazy val bar = Project(id = "bar", base = file("bar"))

  val deleteFileOnExitHook = Command.single("deleteFileOnExitHook") { (state: State, arg: String) =>
    state.addExitHook {
      new File(arg).delete()
    }
  }

  override lazy val settings = super.settings ++ Seq(Keys.commands += deleteFileOnExitHook)
}
