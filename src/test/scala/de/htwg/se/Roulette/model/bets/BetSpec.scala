package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.*
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

  "The Bet companion object" should {

    "return a NumberBet for a valid number string" in {
      val bets = Bet("25")
      bets should have size 1
      bets.head shouldBe a [NumberBet]
      bets.head.asInstanceOf[NumberBet].number shouldBe 25
    }

    "return a RedBet for 'r' or 'red'" in {
      Bet("r").head shouldBe a [RedBet]
      Bet("red").head shouldBe a [RedBet]
      Bet("Red").head shouldBe a [RedBet]
    }

    "return a BlackBet for 'b' or 'black'" in {
      Bet("b").head shouldBe a [BlackBet]
      Bet("black").head shouldBe a [BlackBet]
      Bet("Black").head shouldBe a [BlackBet]
    }

    "return a FirstThirdBet for '1/3'" in {
      Bet("1/3").head shouldBe a [FirstThirdBet]
    }

    "return a SecondThirdBet for '2/3'" in {
      Bet("2/3").head shouldBe a [SecondThirdBet]
    }

    "return a ThirdThirdBet for '3/3'" in {
      Bet("3/3").head shouldBe a [ThirdThirdBet]
    }

    "return an EvenBet for 'even' or 'e'" in {
      Bet("even").head shouldBe a [EvenBet]
      Bet("e").head shouldBe a [EvenBet]
    }

    "return an OddBet for 'odd' or 'o'" in {
      Bet("odd").head shouldBe a [OddBet]
      Bet("o").head shouldBe a [OddBet]
    }

    "return LineBets for '12-1', '22-1', '32-1' or their aliases" in {
      Bet("12-1").head shouldBe a [LineOneBet]
      Bet("2l").head shouldBe a [LineTwoBet]
      Bet("32-1").head shouldBe a [LineThreeBet]
    }

    "return an empty list for invalid input" in {
      Bet("invalid") should be (empty)
      Bet("100") should be (empty) // Out of range
      Bet("-5") should be (empty)  // Out of range
    }

    "parse multiple bets from a single line" in {
      val input = "10 r 2/3"
      val bets = Bet(input)
      bets should have size 3
      bets should contain (NumberBet(10))
      bets should contain (RedBet())
      bets should contain (SecondThirdBet())
    }

    "handle mixed valid and invalid bets" in {
      val input = "15 xyz black"
      val bets = Bet(input)
      bets should have size 2
      bets should contain (NumberBet(15))
      bets should contain (BlackBet())
    }
  }
}
