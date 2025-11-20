package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.{Observer, GameController, ControllerEvent}

class ConsoleObserver(controller: GameController) extends Observer[ControllerEvent] {
  controller.addObserver(this)
  override def update(event: ControllerEvent): Unit =
    println(s"ConsoleObserver received event: $event")
}
