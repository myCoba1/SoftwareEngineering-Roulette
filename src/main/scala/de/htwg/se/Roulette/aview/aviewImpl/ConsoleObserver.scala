package de.htwg.se.Roulette.aview.aviewImpl

import com.google.inject.Inject
import de.htwg.se.Roulette.aview.ConsoleObserverInterface
import de.htwg.se.Roulette.controller.{BetPlaced,BetUndone,ControllerEvent,ControllerInterface,NewRound}
import de.htwg.se.Roulette.model.terminal.PrintTable
import de.htwg.se.Roulette.util.Observer

class ConsoleObserver @Inject() (controller: ControllerInterface) extends Observer[ControllerEvent] 
  with ConsoleObserverInterface {
  controller.addObserver(this)
  override def update(event: ControllerEvent): Unit = {
    event match {
      case NewRound(gs) =>
        println("New round started.")
        println(s"Current Balance: ${gs.balance}")
        println(PrintTable.printTable(11, 1, None))
        println("Place your bet(s) (e.g., '10 r 1/3'), set 'stake <amount>', or type 'new', 'undo', 'quit'.")
      case BetPlaced(gs) =>
        println(s"Bets placed: ${gs.bets.mkString(", ")}")
        gs.bets.foreach { bet =>
          if (bet.isWinningBet(gs.winningNumber)) {
            println(s"You WON on your bet: $bet. Payout: ${bet.payout(gs.winningNumber)}")
          } else {
            println(s"You LOST on your bet: $bet")
          }
        }
        println(s"New Balance: ${gs.balance}")
        if (gs.balance <= 0) println("GAME OVER! You have reached 0 balance.")
        println(PrintTable.printTable(11, 1, Some(gs.winningNumber)))
        println("Type 'new' to start a new round.")
      case BetUndone =>
        println("Last bet was undone. You can place a new bet.")
        println(PrintTable.printTable(11, 1, None))
    }
  }
}
