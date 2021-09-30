package com.github.pixeval.pixevalintro

import com.github.pixeval.pixevalintro.component.App

import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSExportTopLevel, JSImport }
import scala.scalajs.LinkingInfo
import slinky.web.ReactDOM
import slinky.hot
import org.scalajs.dom

object Main {
  @JSImport("resources/index.css", JSImport.Default)
  @js.native
  object MainCSS extends js.Object

  @JSImport("@fluentui/react/dist/css/fabric.min.css", JSImport.Default)
  @js.native
  object FluentUICSS extends js.Object

  private val mainCSS = MainCSS
  private val fluentUICSS = FluentUICSS

  @JSExportTopLevel("main")
  def main(): Unit = {
    if (LinkingInfo.developmentMode) {
      hot.initialize()
    }

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    ReactDOM.render(App(), container)
  }
}
