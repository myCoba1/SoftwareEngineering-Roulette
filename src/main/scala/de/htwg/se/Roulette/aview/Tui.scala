package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.{GameController, PlaceBetCommand}
import de.htwg.se.Roulette.model.Bet

import scala.concurrent.Future
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global

class Tui(controller: GameController) {
  def run(): Unit = {
    Future {
      while (true) {
        val input = StdIn.readLine()
        input.trim.toLowerCase.split("\\s+").toList match {
          case "new" :: Nil => controller.startRound()
          case "undo" :: Nil => controller.undo()
          case "quit" :: Nil => sys.exit(0)
          case betStrings =>
            val bets = Bet(betStrings.mkString(" "))
            if (bets.nonEmpty) {
              controller.executeCommand(new PlaceBetCommand(bets, controller))
            } else {
              println("Invalid command or bet.")
            }
        }
      }
    }
  }
}