package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BetParserSpec extends AnyWordSpec with Matchers {

  "A BetParser chain" should {
    val chain = {
      val numberParser = new NumberBetParser()
      val redParser = new RedBetParser()
      val blackParser = new BlackBetParser()
      val firstThirdParser = new FirstThirdBetParser()
      val secondThirdParser = new SecondThirdBetParser()
      val thirdThirdParser = new ThirdThirdBetParser()
      val firstHalfParser = new FirstHalfBetParser()
      val secondHalfParser = new SecondHalfBetParser()

      numberParser.setNext(redParser).setNext(blackParser)
        .setNext(firstThirdParser).setNext(secondThirdParser).setNext(thirdThirdParser)
        .setNext(firstHalfParser).setNext(secondHalfParser)
      numberParser
    }

    "correctly parse a number bet" in {
      chain.parse("10", 10) shouldBe Some(NumberBet(10, 10))
    }

    "correctly parse a red bet" in {
      chain.parse("r", 10) shouldBe Some(RedBet(10))
    }

    "correctly parse a black bet" in {
      chain.parse("black", 10) shouldBe Some(BlackBet(10))
    }

    "correctly parse a first third bet" in {
      chain.parse("1/3", 10) shouldBe Some(FirstThirdBet(10))
    }

    "correctly parse a second third bet" in {
      chain.parse("2t", 10) shouldBe Some(SecondThirdBet(10))
    }

    "correctly parse a third third bet" in {
      chain.parse("3/3", 10) shouldBe Some(ThirdThirdBet(10))
    }

    "correctly parse a first half bet" in {
      chain.parse("1h", 10) shouldBe Some(FirstHalfBet(10))
    }

    "correctly parse a second half bet" in {
      chain.parse("2/2", 10) shouldBe Some(SecondHalfBet(10))
    }

    "return None for invalid input" in {
      chain.parse("invalid", 10) shouldBe None
    }

    "return None at the end of the chain" in {
      val parser = new NumberBetParser() // A chain with only one link
      parser.parse("red", 10) shouldBe None
    }
  }
}