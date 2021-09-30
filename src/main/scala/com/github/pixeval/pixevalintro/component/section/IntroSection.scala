package com.github.pixeval.pixevalintro.component.section

import com.github.pixeval.pixevalintro.Theme
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport


@react
class IntroSection extends StatelessComponent {
  import IntroSection._
  type Props = Unit

  val css: IntroCSS.type = IntroCSS

  override def render(): ReactElement = section(className := "Intro")(
    p()("基于WinUI3的Pixeval已经正在开发中，而作为旧的WPF版本除严重问题以外不再进行大量维护，请适时切换到新版Pixeval。")
  )
}

object IntroSection {
  @JSImport("resources/Intro.css", JSImport.Default)
  @js.native
  object IntroCSS extends js.Object
}