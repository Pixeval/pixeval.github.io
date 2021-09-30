package com.github.pixeval.pixevalintro

import org.scalajs.dom.raw.{ Element, HTMLCollection }
import org.scalajs.dom.NodeList

object Util {

  implicit class NodeListExt(nodeList: NodeList) {
    def forEach(f: Element => Any): Unit = {
      for (i <- 0 until nodeList.length)
        f(nodeList(i).asInstanceOf[Element])
    }
  }

  implicit class HTMLCollectionExt(htmlCollection: HTMLCollection) {
    def forEach(f: Element => Any): Unit = {
      for (i <- 0 until htmlCollection.length) {
        f(htmlCollection(i))
      }
    }
  }

}
