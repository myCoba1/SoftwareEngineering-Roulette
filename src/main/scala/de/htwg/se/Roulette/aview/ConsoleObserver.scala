package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller._
import de.htwg.se.Roulette.model.PrintTable

class ConsoleObserver(controller: GameController) extends Observer[ControllerEvent] {
  controller.addObserver(this)
  override def update(event: ControllerEvent): Unit = {
    event match {
      case NewRound(gs) =>
        println("New round started.")
        println(PrintTable.printTable(11, 1, None))
        println("Place your bet(s) (e.g., '10 r 1/3'), or type 'new', 'undo', 'quit'.")
      case BetPlaced(gs) =>
        println(s"Bets placed: ${gs.bets.mkString(", ")}")
        gs.bets.foreach { bet =>
          if (bet.isWinningBet(gs.winningNumber)) {
            println(s"You WON on your bet: $bet")
          } else {
            println(s"You LOST on your bet: $bet")
          }
        }
        println(PrintTable.printTable(11, 1, Some(gs.winningNumber)))
        println("Type 'new' to start a new round.")
      case BetUndone =>
        println("Last bet was undone. You can place a new bet.")
        println(PrintTable.printTable(11, 1, None))
    }
  }
}
