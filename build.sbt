import org.scalablytyped.converter.Flavour.Slinky

enablePlugins(ScalaJSBundlerPlugin, ScalablyTypedConverterPlugin)

name := "pixeval-intro"

scalaVersion := "2.13.6"
scalacOptions ++= Seq("-encoding", "UTF-8", "-Ymacro-annotations")
stFlavour := Slinky

// dependencies
Compile / npmDependencies ++= Seq(
  "react" -> "16.14.0",
  "react-dom" -> "16.14.0",
  "react-proxy" -> "1.1.8",
  "@fluentui/react" -> "7.176.0"
)

Compile / npmDevDependencies ++= Seq(
  "file-loader" -> "6.2.0",
  "style-loader" -> "2.0.0",
  "css-loader" -> "5.2.6",
  "html-webpack-plugin" -> "4.5.1",
  "copy-webpack-plugin" -> "6.4.0",
  "webpack-merge" -> "5.8.0",
  "@types/react" -> "16.14.15",
  "@types/react-dom" -> "16.9.14"
)
stIgnore += "react-proxy"

libraryDependencies ++= Seq(
  "me.shadaj" %%% "slinky-core" % "0.6.8",
  "me.shadaj" %%% "slinky-web" % "0.6.8",
  "me.shadaj" %%% "slinky-hot" % "0.6.8",
  "org.scalatest" %%% "scalatest" % "3.2.9" % Test
)

fork := false
// webpack config
webpack / version := "4.44.2"
startWebpackDevServer / version := "3.11.2"
webpackResources := baseDirectory.value / "webpack" * "*"

fastOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js")
fullOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js")
Test / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-core.config.js")

fastOptJS / webpackDevServerExtraArgs := Seq("--inline", "--hot")
fastOptJS / webpackBundlingMode := BundlingMode.LibraryOnly()

Test / requireJsDomEnv := true

lazy val devCompile = taskKey[Unit]("fastOptJS::webpack")
devCompile := {
  val f = new File("target/scala-2.13/scalajs-bundler/main/dist/")
  if (f.exists && f.isDirectory)
    f.listFiles((dir, name) => name.endsWith(".js")).headOption match {
      case Some(js) => js.renameTo(new File(js.getAbsolutePath + ".bak"))
      case None =>
    }

  Command.process("Compile / fastOptJS / webpack", state.value)

  val files = f.listFiles()

  files.find(_.name.endsWith(".js")) match {
    case Some(js) => new File(js.getAbsolutePath + ".bak").delete()
    case None => files.find(_.name.endsWith(".js.bak")) match {
      case Some(jsBak) => jsBak.renameTo(new File(jsBak.getAbsolutePath.replace(".bak", "")))
      case None =>
    }
  }
}

addCommandAlias("dev", "~devCompile")
addCommandAlias("build", "fullOptJS::webpack")
