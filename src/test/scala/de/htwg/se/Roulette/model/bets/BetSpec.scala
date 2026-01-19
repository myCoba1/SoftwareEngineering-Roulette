package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BetSpec extends AnyWordSpec with Matchers {
  "A Bet" should {
    "be checkable for a win" in {
      // Create an anonymous implementation of the Bet trait for testing
      val winningBet = new Bet {
        override def amount: Int = 10
        override def isWinningBet(winningNumber: Int): Boolean = winningNumber == 5
        override def multiplier: Int = 1
      }

      winningBet.isWinningBet(5) should be(true)
      winningBet.isWinningBet(10) should be(false)
    }
    
    "calculate payout correctly" in {
      val bet = new Bet {
        override def amount: Int = 10
        override def isWinningBet(winningNumber: Int): Boolean = winningNumber == 5
        override def multiplier: Int = 2
      }
      
      // Win: 10 * (2 + 1) = 30
      bet.payout(5) should be(30)
      
      // Loss: 0
      bet.payout(6) should be(0)
    }
  }

  "The Bet companion object" should {

    "return a NumberBet for a valid number string" in {
      val bets = Bet("25", 10)
      bets should have size 1
      bets.head shouldBe a [NumberBet]
      bets.head.asInstanceOf[NumberBet].number shouldBe 25
      bets.head.amount shouldBe 10
    }

    "return a RedBet for 'r' or 'red'" in {
      Bet("r", 10).head shouldBe a [RedBet]
      Bet("red", 10).head shouldBe a [RedBet]
      Bet("Red", 10).head shouldBe a [RedBet]
    }

    "return a BlackBet for 'b' or 'black'" in {
      Bet("b", 10).head shouldBe a [BlackBet]
      Bet("black", 10).head shouldBe a [BlackBet]
      Bet("Black", 10).head shouldBe a [BlackBet]
    }

    "return a FirstThirdBet for '1/3'" in {
      Bet("1/3", 10).head shouldBe a [FirstThirdBet]
    }

    "return a SecondThirdBet for '2/3'" in {
      Bet("2/3", 10).head shouldBe a [SecondThirdBet]
    }

    "return a ThirdThirdBet for '3/3'" in {
      Bet("3/3", 10).head shouldBe a [ThirdThirdBet]
    }

    "return an EvenBet for 'even' or 'e'" in {
      Bet("even", 10).head shouldBe a [EvenBet]
      Bet("e", 10).head shouldBe a [EvenBet]
    }

    "return an OddBet for 'odd' or 'o'" in {
      Bet("odd", 10).head shouldBe a [OddBet]
      Bet("o", 10).head shouldBe a [OddBet]
    }

    "return LineBets for '12-1', '22-1', '32-1' or their aliases" in {
      Bet("12-1", 10).head shouldBe a [LineOneBet]
      Bet("2l", 10).head shouldBe a [LineTwoBet]
      Bet("32-1", 10).head shouldBe a [LineThreeBet]
    }
    
    "return Halves bets" in {
      Bet("1/2", 10).head shouldBe a [FirstHalfBet]
      Bet("2/2", 10).head shouldBe a [SecondHalfBet]
    }

    "return an empty list for invalid input" in {
      Bet("invalid", 10) should be (empty)
      Bet("100", 10) should be (empty) // Out of range
      Bet("-5", 10) should be (empty)  // Out of range
    }

    "parse multiple bets from a single line" in {
      val input = "10 r 2/3"
      val bets = Bet(input, 10)
      bets should have size 3
      bets should contain (NumberBet(10, 10))
      bets should contain (RedBet(10))
      bets should contain (SecondThirdBet(10))
    }

    "handle mixed valid and invalid bets" in {
      val input = "15 xyz black"
      val bets = Bet(input, 10)
      bets should have size 2
      bets should contain (NumberBet(15, 10))
      bets should contain (BlackBet(10))
    }
  }
}
