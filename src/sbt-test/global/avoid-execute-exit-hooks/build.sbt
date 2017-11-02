lazy val foo = project in file("foo")
lazy val bar = project in file("bar")

val deleteFileOnExitHook = Command.single("deleteFileOnExitHook") { (state: State, arg: String) =>
  state.addExitHook {
    new File(arg).delete()
  }
}

lazy val root = (project in file("."))
  .settings(Keys.commands += deleteFileOnExitHook)
