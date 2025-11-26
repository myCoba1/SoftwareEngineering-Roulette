package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.GameController
import de.htwg.se.Roulette.model.{Bet, BetFactory}

object PlaceBet {
  def placeBet(controller: GameController, randomInt: Int): Boolean = {
    var bets: List[Bet] = List.empty
    while (bets.isEmpty) {
      print("Place your Bet(s) (R 1/3 22): ")
      val line = scala.io.StdIn.readLine()
      if (line != null) {
        bets = BetFactory.getBets(line)
        if (bets.isEmpty) {
          println("Invalid input. Please enter one or more valid bets (0-36, R/B, or 1/3, 2/3, 3/3)")
        }
      }
    }

    bets.foreach { bet =>
      if (bet.isWinningBet(randomInt)) {
        println(s"You won on your bet: $bet")
      } else {
        println(s"You lost on your bet: $bet")
      }
    }
    controller.placeBet(bets, randomInt)

    true
  }
}
