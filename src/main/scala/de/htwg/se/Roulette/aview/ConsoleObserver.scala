package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller._

class ConsoleObserver(controller: GameController) extends Observer[ControllerEvent] {
  controller.addObserver(this)
  override def update(event: ControllerEvent): Unit =
    event match {
      case NewRound(_) =>
        println(s"ConsoleObserver: New round started. ")
//        println(s"ConsoleObserver: Winning number: ${gs.winningNumber}")
      case BetPlaced(gs) =>
        println(s"ConsoleObserver: Bets placed: ${gs.bets.mkString(", ")} on result ${gs.winningNumber}")
      case BetUndone =>
        println("ConsoleObserver: Last round was undone.")
      case e => println(s"ConsoleObserver received event: $e")
    }
}
