package de.htwg.se.Roulette.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BetSpec extends AnyWordSpec with Matchers {
  "A Bet" should {
    "be checkable for a win" in {
      // Create an anonymous implementation of the Bet trait for testing
      val winningBet = new Bet {
        override def isWinningBet(winningNumber: Int): Boolean = winningNumber == 5
      }

      winningBet.isWinningBet(5) should be(true)
      winningBet.isWinningBet(10) should be(false)
    }
  }
}
