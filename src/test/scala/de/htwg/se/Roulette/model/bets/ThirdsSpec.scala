package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.{FirstThirdBet, SecondThirdBet, ThirdThirdBet, Thirds}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ThirdsSpec extends AnyWordSpec with Matchers {

  "Thirds.thirdOf" should {

    "return 1/3 for numbers 1–12" in {
      (1 to 12).foreach { n =>
        Thirds.thirdOf(n) shouldBe "1/3"
      }
    }

    "return 2/3 for numbers 13–24" in {
      (13 to 24).foreach { n =>
        Thirds.thirdOf(n) shouldBe "2/3"
      }
    }

    "return 3/3 for numbers 25–36" in {
      (25 to 36).foreach { n =>
        Thirds.thirdOf(n) shouldBe "3/3"
      }
    }

    "return None for 0" in {
      Thirds.thirdOf(0) shouldBe "None"
    }

    "return None for numbers outside 1–36" in {
      Thirds.thirdOf(-1) shouldBe "None"
      Thirds.thirdOf(100) shouldBe "None"
    }
  }

  "A FirstThirdBet" should {
    val bet = FirstThirdBet(10)
    "win for numbers in the first third" in {
      (1 to 12).foreach { n =>
        bet.isWinningBet(n) shouldBe true
      }
    }
    "lose for numbers outside the first third" in {
      ((13 to 36).toSet + 0).foreach { n =>
        bet.isWinningBet(n) shouldBe false
      }
    }
    "have a correct string representation" in {
      bet.toString shouldBe "1 st 12 (10)"
    }
  }

  "A SecondThirdBet" should {
    val bet = SecondThirdBet(10)
    "win for numbers in the second third" in {
      (13 to 24).foreach { n =>
        bet.isWinningBet(n) shouldBe true
      }
    }
    "lose for numbers outside the second third" in {
      ((1 to 12).toSet ++ (25 to 36).toSet + 0).foreach { n =>
        bet.isWinningBet(n) shouldBe false
      }
    }
    "have a correct string representation" in {
      bet.toString shouldBe "2 nd 12 (10)"
    }
  }

  "A ThirdThirdBet" should {
    val bet = ThirdThirdBet(10)
    "win for numbers in the third third" in {
      (25 to 36).foreach { n =>
        bet.isWinningBet(n) shouldBe true
      }
    }
    "lose for numbers outside the third third" in {
      ((1 to 24).toSet + 0).foreach { n =>
        bet.isWinningBet(n) shouldBe false
      }
    }
    "have a correct string representation" in {
      bet.toString shouldBe "3 rd 12 (10)"
    }
  }
}
