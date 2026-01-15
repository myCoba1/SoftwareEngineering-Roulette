package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class EvenOddSpec extends AnyWordSpec with Matchers {
  "EvenOdd" should {
    "identify even numbers correctly" in {
      EvenOdd.evenOr(2) should be("even")
      EvenOdd.evenOr(36) should be("even")
    }
    "identify odd numbers correctly" in {
      EvenOdd.evenOr(1) should be("odd")
      EvenOdd.evenOr(35) should be("odd")
    }
    "return None for 0 or invalid numbers" in {
      EvenOdd.evenOr(0) should be("None")
      EvenOdd.evenOr(-1) should be("None")
    }
  }

  "EvenBet" should {
    val bet = EvenBet()
    "win on even numbers" in {
      bet.isWinningBet(2) should be(true)
      bet.isWinningBet(10) should be(true)
    }
    "lose on odd numbers" in {
      bet.isWinningBet(1) should be(false)
      bet.isWinningBet(11) should be(false)
    }
    "lose on 0" in {
      bet.isWinningBet(0) should be(false)
    }
    "have correct string representation" in {
      bet.toString should be("Even")
    }
  }

  "OddBet" should {
    val bet = OddBet()
    "win on odd numbers" in {
      bet.isWinningBet(1) should be(true)
      bet.isWinningBet(11) should be(true)
    }
    "lose on even numbers" in {
      bet.isWinningBet(2) should be(false)
      bet.isWinningBet(10) should be(false)
    }
    "lose on 0" in {
      bet.isWinningBet(0) should be(false)
    }
    "have correct string representation" in {
      bet.toString should be("Odd")
    }
  }
}