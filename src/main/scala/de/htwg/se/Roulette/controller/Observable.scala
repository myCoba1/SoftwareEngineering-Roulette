package de.htwg.se.Roulette.controller

import scala.collection.mutable.ListBuffer

trait Observable[E] {
  private val observers = ListBuffer.empty[Observer[E]]

  def addObserver(o: Observer[E]): Unit = this.synchronized {
    observers += o
  }

  def removeObserver(o: Observer[E]): Unit = this.synchronized {
    observers -= o
  }

  protected def notifyObservers(event: E): Unit = this.synchronized {
    observers.foreach(_.update(event))
  }
}
