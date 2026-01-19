package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.{BlackBet, RedBet, RedBlack}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RedBlackSpec extends AnyWordSpec with Matchers {

  "RedBlack.colorOf" should {

    "return G for 0" in {
      RedBlack.colorOf(0) shouldBe 'G'
    }

    "return R for all red numbers" in {
      val reds = Set(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36)
      reds.foreach(n => RedBlack.colorOf(n) shouldBe 'R')
    }

    "return B for all black numbers" in {
      RedBlack.blacks.foreach(n => RedBlack.colorOf(n) shouldBe 'B')
    }

    "return B for numbers outside 0–36" in {
      RedBlack.colorOf(100) shouldBe 'B'
      RedBlack.colorOf(-5) shouldBe 'B'
    }
  }

  "A RedBet" should {
    val redBet = RedBet()

    "win if the number is red" in {
      RedBlack.reds.foreach { redNumber =>
        redBet.isWinningBet(redNumber) should be(true)
      }
    }

    "lose if the number is black or green" in {
      (RedBlack.blacks + 0).foreach { nonRedNumber =>
        redBet.isWinningBet(nonRedNumber) should be(false)
      }
    }

    "have a correct string representation" in {
      redBet.toString shouldBe "Red"
    }
  }

  "A BlackBet" should {
    val blackBet = BlackBet()

    "win if the number is black" in {
      RedBlack.blacks.foreach { blackNumber =>
        blackBet.isWinningBet(blackNumber) should be(true)
      }
    }

    "lose if the number is red or green" in {
      (RedBlack.reds + 0).foreach { nonBlackNumber =>
        blackBet.isWinningBet(nonBlackNumber) should be(false)
      }
    }

    "have a correct string representation" in {
      blackBet.toString shouldBe "Black"
    }
  }
}
