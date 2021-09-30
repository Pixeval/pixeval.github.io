package com.github.pixeval.pixevalintro

import typings.fluentuiTheme.ipaletteMod.IPalette
import typings.fluentuiTheme.themeMod.{ Theme => ITheme }

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Theme {
  @JSImport("js/theme.js", "lightTheme")
  @js.native
  private object Light extends ITheme

  @JSImport("js/theme.js", "darkTheme")
  @js.native
  private object Dark extends ITheme

  @JSImport("js/theme.js", "isDarkMode")
  @js.native
  private val isDarkMode: js.Function0[Boolean] = js.native

  def palette: IPalette = if (isDarkMode()) Dark.palette else Light.palette

}