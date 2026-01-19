package de.htwg.se.Roulette.aview.aviewImpl

import com.google.inject.Inject
import de.htwg.se.Roulette.controller.ControllerInterface
import de.htwg.se.Roulette.model.bets.Bet

import scala.io.StdIn

class Tui @Inject() (controller: ControllerInterface) {
  def run(): Unit = {
      while (true) {
        val input = StdIn.readLine()
        input.trim.toLowerCase.split("\\s+").toList match {
          case "new" :: Nil => controller.startRound()
          case "undo" :: Nil => controller.undo()
          case "save" :: Nil => controller.save()
          case "load" :: Nil => controller.load()
          case "quit" :: Nil => sys.exit(0)
          case betStrings =>
            val bets = Bet(betStrings.mkString(" "))
            if (bets.nonEmpty) {
              controller.placeBet(bets)
            } else {
              println("Invalid command or bet.")
            }
        }
      }
  }
}