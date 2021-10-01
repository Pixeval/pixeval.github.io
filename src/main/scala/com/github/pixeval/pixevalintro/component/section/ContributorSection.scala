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

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSImport
import scala.util.{ Failure, Success }


@react
class ContributorSection extends Component {
  import ContributorSection._

  type Props = Unit
  case class State(
    texts: Seq[String], imageUrls: Seq[String], secondaryTexts: Seq[String], hrefs: Seq[String], isMain: Seq[Boolean]
  ) {
    def toPersonaProps: Seq[PersonaProps] = texts.indices.map { i =>
      PersonaProps(imageUrls(i), texts(i), secondaryTexts(i), isMain(i))
    }
  }

  override def initialState: State = State(Nil, Nil, Nil, Nil, Nil)
  val css: ContributorCSS.type = ContributorCSS

  override def componentWillMount(): Unit = {
    Ajax.get("https://api.github.com/repos/Pixeval/Pixeval/contributors").onComplete {
      case Success(value) => {
        val contributors = JSON.parse(value.responseText).asInstanceOf[js.Array[Contributor]].toSeq
        val order: Seq[Int] = centralizeContributors(contributors.size)
        val texts: Seq[String] = order map contributors.map(_.login)
        val imageUrls: Seq[String] = order map contributors.map(_.avatar_url)
        val secondaryTexts: Seq[String] = order map contributors.map(info => s"贡献: ${ info.contributions }")
        val hrefs: Seq[String] = order map contributors.map(_.html_url)
        val isMain: Seq[Boolean] = order map (true :: List.fill(contributors.size - 1)(false))

        val namesFuture = Future.traverse(texts)(getUserName)
        namesFuture onComplete {
          case Success(newTexts) => this.setState(State(newTexts, imageUrls, secondaryTexts, hrefs, isMain))
          case Failure(_) => throw new Exception()
        }
      }
      case Failure(_) => throw new Exception()
    }
  }

  private def getUserName(login: String): Future[String] =
    Ajax.get(s"https://api.github.com/users/$login").map {
      response => {
        val user = JSON.parse(response.responseText).asInstanceOf[GithubUser]
        val name = user.name
        if (name == null) login
        else name
      }
    }

  final val headerDesc: String = "贡献者"

  private def renderPersona(): ReactElement = Stack.withProps(IStackProps()
    .setId("Contributor-stack-div")
    .setHorizontal(true)
    .setVerticalAlign(Alignment.center)
    .setHorizontalAlign(Alignment.center)
    .setClassName("Contributor-stack").asInstanceOf[IStackProps with Children])(

    this.state.toPersonaProps.map(_.asJS).map(Persona.withProps)
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
    if (this.state.isMain.nonEmpty)
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
  trait Contributor extends js.Object {
    val login: String
    val avatar_url: String
    val contributions: Int
    val html_url: String
  }

  @js.native
  trait GithubUser extends js.Object {
    val login: String
    val name: String
  }

  case class PersonaProps(imageUrl: String, text: String, secondaryText: String, isMain: Boolean) {

    def getSize: PersonaSize with Double = if (isMain) mainSize else otherSize

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
