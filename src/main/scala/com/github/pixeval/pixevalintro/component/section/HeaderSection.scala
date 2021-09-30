package com.github.pixeval.pixevalintro.component.section

import com.github.pixeval.pixevalintro.component.tool.AImg
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import typings.fluentuiTheme.mod.FontSizes

import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal
import scala.scalajs.js.annotation.JSImport

@react
class HeaderSection extends StatelessComponent {
  import HeaderSection._
  type Props = Unit

  val css: HeaderCSS.type = HeaderCSS

  val logo = "https://s1.ax1x.com/2020/04/03/GUMZjS.png"
  val headerDesc = "基于.NET 5和WinUI 3的强大、快速、漂亮的Pixiv桌面程序"

  override def render(): ReactElement =
    section(className := "Header")(
      header(className := "Header-header")(
        img(src := logo, className := "Header-logo", alt := "logo"),
        h2(className := "Header-title", style := literal(fontSize = FontSizes.size24))(headerDesc),
      p(style := literal(`text-align` = "center"), className := "Header-badges")(
        AImg("https://github.com/Pixeval/Pixeval", "https://img.shields.io/github/stars/Rinacm/Pixeval?color=red&style=flat-square", "_blank"),
        AImg("mailto:decem0730@hotmail.com", "https://img.shields.io/static/v1?label=contact%20me&message=hotmail&color=green&style=flat-square"),
        AImg("https://jq.qq.com/?_wv=1027&k=5hGmJbQ", "https://img.shields.io/static/v1?label=chatting&message=qq&color=blue&style=flat-square", "_blank"),
        AImg("https://pixeval.github.io/", "https://img.shields.io/static/v1?label=homepage&message=pixeval&color=blueviolet&style=flat-square", "_blank"),
        AImg("https://github.com/Rinacm/Pixeval/blob/master/LICENSE", "https://img.shields.io/github/license/Rinacm/Pixeval?style=flat-square", "_blank"),
        AImg("https://github.com/Rinacm/Pixeval/issues/new/choose", "https://img.shields.io/static/v1?label=feedback&message=issues&color=pink&style=flat-square", "_blank"),
        AImg("https://dotnet.microsoft.com/download/dotnet/thank-you/runtime-desktop-5.0.9-windows-x64-installer", "https://img.shields.io/static/v1?label=runtime&message=.NET%205.0&color=yellow&style=flat-square", "_blank")
      )
    ))
}

object HeaderSection {
  @JSImport("resources/Header.css", JSImport.Default)
  @js.native
  object HeaderCSS extends js.Object
}