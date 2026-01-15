package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.model.{GameState, PrintTable}
import de.htwg.se.Roulette.controller.ControllerInterface

object RouletteRound {
  def rouletteRound(controller: ControllerInterface): RoundAction = {
    controller.gameState match {
      case Some(GameState(_, bets)) =>
        val emptyTable = PrintTable.printTable(11, 1, None)
        println(emptyTable)

        if (bets.isEmpty) {
          PlaceBet.placeBet(controller)
        }

        controller.gameState.foreach { updatedGs =>
          updatedGs.bets.foreach { bet =>
            if (bet.isWinningBet(updatedGs.winningNumber)) {
              println(s"You won on your bet: $bet")
            } else {
              println(s"You lost on your bet: $bet")
            }
          }

          val trueTable = PrintTable.printTable(11, 1, Some(updatedGs.winningNumber))
          println(trueTable)
        }
        promptForNextAction(controller)
      case None =>
        println("Error: No game state. Cannot play round.")
        Quit
    }
  }

  private def promptForNextAction(controller: ControllerInterface): RoundAction = {
    val input = scala.io.StdIn.readLine("Play another round? (y/n/undo): ").trim.toLowerCase
    input match {
      case "y" => Continue
      case "undo" =>
        controller.undo()
        Undo
      case _ => Quit
    }
  }
}
