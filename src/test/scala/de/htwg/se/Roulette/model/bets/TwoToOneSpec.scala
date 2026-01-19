package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.{LineOneBet, LineThreeBet, LineTwoBet, TwoToOne}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TwoToOneSpec extends AnyWordSpec with Matchers {
  "TwoToOne" should {
    "identify numbers in line one" in {
      TwoToOne.lineOf(1) should be("1. 2-1")
      TwoToOne.lineOf(4) should be("1. 2-1")
      TwoToOne.lineOf(34) should be("1. 2-1")
    }
    "identify numbers in line two" in {
      TwoToOne.lineOf(2) should be("2. 2-1")
      TwoToOne.lineOf(5) should be("2. 2-1")
      TwoToOne.lineOf(35) should be("2. 2-1")
    }
    "identify numbers in line three" in {
      TwoToOne.lineOf(3) should be("3. 2-1")
      TwoToOne.lineOf(6) should be("3. 2-1")
      TwoToOne.lineOf(36) should be("3. 2-1")
    }
    "return None for 0 or invalid numbers" in {
      TwoToOne.lineOf(0) should be("None")
      TwoToOne.lineOf(37) should be("None")
    }
  }

  "LineOneBet" should {
    val bet = LineOneBet(10)
    "win on numbers in line one" in {
      bet.isWinningBet(1) should be(true)
      bet.isWinningBet(34) should be(true)
    }
    "lose on numbers not in line one" in {
      bet.isWinningBet(2) should be(false)
      bet.isWinningBet(3) should be(false)
      bet.isWinningBet(0) should be(false)
    }
    "have correct string representation" in {
      bet.toString should be("1. 2-1 (10)")
    }
  }

  "LineTwoBet" should {
    val bet = LineTwoBet(10)
    "win on numbers in line two" in {
      bet.isWinningBet(2) should be(true)
      bet.isWinningBet(35) should be(true)
    }
    "lose on numbers not in line two" in {
      bet.isWinningBet(1) should be(false)
      bet.isWinningBet(3) should be(false)
      bet.isWinningBet(0) should be(false)
    }
    "have correct string representation" in {
      bet.toString should be("2. 2-1 (10)")
    }
  }

  "LineThreeBet" should {
    val bet = LineThreeBet(10)
    "win on numbers in line three" in {
      bet.isWinningBet(3) should be(true)
      bet.isWinningBet(36) should be(true)
    }
    "lose on numbers not in line three" in {
      bet.isWinningBet(1) should be(false)
      bet.isWinningBet(2) should be(false)
      bet.isWinningBet(0) should be(false)
    }
    "have correct string representation" in {
      bet.toString should be("3. 2-1 (10)")
    }
  }
}