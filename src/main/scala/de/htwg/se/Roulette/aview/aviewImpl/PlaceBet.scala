package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.controller.ControllerInterface
import de.htwg.se.Roulette.model.bets.Bet

object PlaceBet {
  def placeBet(controller: ControllerInterface): Unit = {
    var bets: List[Bet] = List.empty

    while (bets.isEmpty) {
      print("Place your Bet(s) (e.g., R 1/3 22): ")
      val line = scala.io.StdIn.readLine()

      Option(line).map(_.trim.toLowerCase) match {
        case Some(input) if input.nonEmpty =>
          bets = Bet(line)
          if (bets.isEmpty) {
            println("Invalid input. Please enter one or more valid bets (0-36, R/B, or 1/3, 2/3, 3/3)")
          } else {
            controller.placeBet(bets)
          }
        case _ => //null or empty input
      }
    }
  }
}