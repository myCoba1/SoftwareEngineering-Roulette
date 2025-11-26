package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.{BetPlaced, ControllerEvent, GameController, Observer}

class ConsoleObserver(controller: GameController) extends Observer[ControllerEvent] {
  controller.addObserver(this)
  override def update(event: ControllerEvent): Unit =
    event match {
      case BetPlaced(bets, result) =>
        println(s"ConsoleObserver: Bets placed: ${bets.mkString(", ")} on result $result")
      case e => println(s"ConsoleObserver received event: $e")
    }
}
