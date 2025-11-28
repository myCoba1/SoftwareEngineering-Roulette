package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class BetFactorySpec extends AnyWordSpec with Matchers {

  "BetFactory.getBets" should {

    "return a NumberBet for a valid number string" in {
      val bets = BetFactory.getBets("25")
      bets should have size 1
      bets.head shouldBe a [NumberBet]
      bets.head.asInstanceOf[NumberBet].number shouldBe 25
    }

    "return a RedBet for 'r' or 'red'" in {
      BetFactory.getBets("r").head shouldBe a [RedBet]
      BetFactory.getBets("red").head shouldBe a [RedBet]
      BetFactory.getBets("Red").head shouldBe a [RedBet]
    }

    "return a BlackBet for 'b' or 'black'" in {
      BetFactory.getBets("b").head shouldBe a [BlackBet]
      BetFactory.getBets("black").head shouldBe a [BlackBet]
      BetFactory.getBets("Black").head shouldBe a [BlackBet]
    }

    "return a FirstThirdBet for '1/3'" in {
      BetFactory.getBets("1/3").head shouldBe a [FirstThirdBet]
    }

    "return a SecondThirdBet for '2/3'" in {
      BetFactory.getBets("2/3").head shouldBe a [SecondThirdBet]
    }

    "return a ThirdThirdBet for '3/3'" in {
      BetFactory.getBets("3/3").head shouldBe a [ThirdThirdBet]
    }

    "return an empty list for invalid input" in {
      BetFactory.getBets("invalid") should be (empty)
      BetFactory.getBets("100") should be (empty) // Out of range
      BetFactory.getBets("-5") should be (empty)  // Out of range
    }

    "parse multiple bets from a single line" in {
      val input = "10 r 2/3"
      val bets = BetFactory.getBets(input)
      bets should have size 3
      bets should contain (NumberBet(10))
      bets should contain (RedBet())
      bets should contain (SecondThirdBet())
    }

    "handle mixed valid and invalid bets" in {
      val input = "15 xyz black"
      val bets = BetFactory.getBets(input)
      bets should have size 2
      bets should contain (NumberBet(15))
      bets should contain (BlackBet())
    }
  }
}