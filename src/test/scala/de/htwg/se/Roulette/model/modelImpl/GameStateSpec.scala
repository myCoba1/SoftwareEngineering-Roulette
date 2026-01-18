package de.htwg.se.Roulette.model.modelImpl

import de.htwg.se.Roulette.model.bets.RedBet
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameStateSpec extends AnyWordSpec with Matchers {
  "A GameState" should {
    "store the winning number and bets" in {
      val bets = List(RedBet())
      val gameState = GameState(10, bets)
      gameState.winningNumber should be(10)
      gameState.bets should be(bets)
    }

    "have an empty list of bets by default" in {
      val gameState = GameState(5)
      gameState.bets should be(empty)
    }
  }
}