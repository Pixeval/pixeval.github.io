package com.github.pixeval.pixevalintro.component

import com.github.pixeval.pixevalintro.component.section.{ HeaderSection, IntroSection }
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.div

@react class App extends StatelessComponent {
  type Props = Unit

  def render(): ReactElement = div(
    HeaderSection(),
    IntroSection()
  )
}
