package com.github.pixeval.pixevalintro.component

import com.github.pixeval.pixevalintro.Theme
import com.github.pixeval.pixevalintro.component.section.{ ContributorSection, HeaderSection, IntroSection }
import org.scalajs.dom.document
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.{ div, style }

import scala.scalajs.js

@react class App extends StatelessComponent {
  type Props = Unit

  override def componentWillMount(): Unit = {
    document.getElementsByTagName("body")(0)
      .setAttribute("style", s"background: ${Theme.palette.neutralLighterAlt}")
  }

  def render(): ReactElement = div(
    style := js.Dynamic.literal(
      color = Theme.palette.neutralPrimary,
      background = Theme.palette.neutralLighterAlt
    )
  )(
    HeaderSection(),
    ContributorSection(),
    IntroSection()
  )
}
