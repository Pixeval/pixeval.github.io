package com.github.pixeval.pixevalintro.component.tool

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

@react
class AImg extends StatelessComponent {
  case class Props(aHref: String, imgSrc: String, aTarget: String = null)
  override def render(): ReactElement = a(href := props.aHref, target := props.aTarget)(img(src := props.imgSrc))
}
