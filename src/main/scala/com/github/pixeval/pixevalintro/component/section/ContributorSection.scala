package com.github.pixeval.pixevalintro.component.section

import com.github.pixeval.pixevalintro.Theme
import com.github.pixeval.pixevalintro.Util.NodeListExt
import com.github.pixeval.pixevalintro.component.section.ContributorSection.PersonaProps.{ mainSize, otherSize }
import org.scalajs.dom.document
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.raw.{ Element, HTMLCollection }
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import typings.fluentuiReact.components.{ Persona, Stack }
import typings.fluentuiTheme.mod.FontSizes
import typings.officeUiFabricReact.personaTypesMod.{ IPersonaProps, PersonaSize }
import typings.officeUiFabricReact.stackTypesMod.{ Alignment, IStackProps }
import typings.react.anon.Children

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSImport
import scala.util.{ Failure, Success }


@react
class ContributorSection extends Component {
  import ContributorSection._

  type Props = Unit
  case class State(personas: Seq[IPersonaProps], hrefs: Seq[String])
  override def initialState: State = State(Nil, Nil)
  val css: ContributorCSS.type = ContributorCSS

  override def componentWillMount(): Unit = {
    Ajax.get("https://api.github.com/repos/Pixeval/Pixeval/contributors").onComplete {
      case Success(value) => {
        val contributors = JSON.parse(value.responseText).asInstanceOf[js.Array[GithubUser]].toSeq
        val personas: Seq[IPersonaProps] = contributors.zipWithIndex.map {
          case (user, i) => new PersonaProps(user, i).asJS
        }
        val hrefs: Seq[String] = contributors.map(_.html_url)
        this.setState(State(centralizeContributors(personas.size) map personas, hrefs))
      }
      case Failure(_) => throw new Exception()
    }
  }

  final val headerDesc: String = "贡献者"

  private def renderPersona(): ReactElement = Stack.withProps(IStackProps()
    .setId("Contributor-stack-div")
    .setHorizontal(true)
    .setVerticalAlign(Alignment.center)
    .setHorizontalAlign(Alignment.center)
    .setClassName("Contributor-stack").asInstanceOf[IStackProps with Children])(

    this.state.personas.map(Persona.withProps)
  )

  private def addAForPersonas(): Unit = {
    val stack: Element = document.getElementById("Contributor-stack-div")
    val children: HTMLCollection = stack.children
    for (i <- 0 until children.length) {
      val personaDiv = children(i)
      val avatarDiv = personaDiv.firstChild
      val descDiv = personaDiv.lastChild
      val a = document.createElement("a")
      a.setAttribute("href", this.state.hrefs(i))
      a.appendChild(avatarDiv)
      personaDiv.appendChild(a)
      personaDiv.appendChild(descDiv)
    }
  }

  private def definePersonaTextColor(): Unit = {
    val primaryTexts = document.querySelectorAll("#Contributor-stack-div div.ms-Persona-primaryText")
    val secondaryTexts = document.querySelectorAll("#Contributor-stack-div div.ms-Persona-secondaryText")

    primaryTexts.forEach {
      _.setAttribute("style", s"color: ${Theme.palette.neutralDark}")
    }

    secondaryTexts.forEach {
      _.setAttribute("style", s"color: ${Theme.palette.neutralSecondary};")
    }
  }

  override def componentDidUpdate(prevProps: Props, prevState: State): Unit = {
    addAForPersonas()
    definePersonaTextColor()
  }

  override def render(): ReactElement = {
    if (this.state.personas.nonEmpty)
      section(className := "Contributor")(

        h2(className := "Contributor-title", style := js.Dynamic.literal(
          fontSize = FontSizes.size32,
          color = Theme.palette.themePrimary
        ))(headerDesc),

        renderPersona()
      )
    else section(className := "Contributor")()
  }
}

object ContributorSection {
  @JSImport("resources/Contributor.css", JSImport.Default)
  @js.native
  object ContributorCSS extends js.Object

  @js.native
  trait GithubUser extends js.Object {
    val login: String
    val avatar_url: String
    val contributions: Int
    val html_url: String
  }

  class PersonaProps(info: GithubUser, i: Int) {
    val imageUrl: String = info.avatar_url
    val text: String = info.login
    val secondaryText: String = s"贡献: ${ info.contributions }"

    def getSize: PersonaSize with Double = if (i == 0) mainSize else otherSize

    def asJS: IPersonaProps = {
      val obj = IPersonaProps()
      obj.setImageUrl(imageUrl)
        .setText(text)
        .setSecondaryText(secondaryText)
        .setSize(getSize)
        .set("key", text)
      obj
    }
  }

  object PersonaProps {
    val mainSize: PersonaSize.size72 with Double = PersonaSize.size72
    val otherSize: PersonaSize.size48 with Double = PersonaSize.size48
  }

  def centralizeContributors(number: Int): List[Int] = {
    val items: List[Int] = (0 until number).toList
    contributorsAppendTail(Nil, items)
  }

  def contributorsAppendHead(order: List[Int], remain: List[Int]): List[Int] = remain match {
    case head :: tail => contributorsAppendTail(head :: order, tail)
    case Nil => order
  }

  def contributorsAppendTail(order: List[Int], remain: List[Int]): List[Int] = remain match {
    case head :: tail => contributorsAppendHead(order :+ head, tail)
    case Nil => order
  }
}
