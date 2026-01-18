package de.htwg.se.Roulette.model

import de.htwg.se.Roulette.model.bets.{FirstHalfBet, Halves, SecondHalfBet}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class HalvesSpec extends AnyWordSpec with Matchers {

  "Halves.halfOf" should {

    "return 1/2 for numbers 1-18" in {
      (1 to 18).foreach { n =>
        Halves.halfOf(n) shouldBe "1/2"
      }
    }

    "return 2/2 for numbers 19-36" in {
      (19 to 36).foreach { n =>
        Halves.halfOf(n) shouldBe "2/2"
      }
    }

    "return None for 0 and numbers outside 1-36" in {
      Halves.halfOf(0) shouldBe "None"
      Halves.halfOf(37) shouldBe "None"
      Halves.halfOf(-1) shouldBe "None"
    }
  }

  "A FirstHalfBet" should {
    val bet = FirstHalfBet()
    "win for numbers in the first half" in {
      bet.isWinningBet(1) shouldBe true
      bet.isWinningBet(18) shouldBe true
    }
    "lose for numbers outside the first half" in {
      bet.isWinningBet(19) shouldBe false
      bet.isWinningBet(0) shouldBe false
    }
    "have a correct string representation" in {
      bet.toString shouldBe "1 to 18"
    }
  }

  "A SecondHalfBet" should {
    val bet = SecondHalfBet()
    "win for numbers in the second half" in {
      bet.isWinningBet(19) shouldBe true
      bet.isWinningBet(36) shouldBe true
    }
    "lose for numbers outside the second half" in {
      bet.isWinningBet(18) shouldBe false
      bet.isWinningBet(0) shouldBe false
    }
    "have a correct string representation" in {
      bet.toString shouldBe "19 to 36"
    }
  }
}