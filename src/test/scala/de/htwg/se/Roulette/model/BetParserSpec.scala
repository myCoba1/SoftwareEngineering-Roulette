package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

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
      chain.parse("10") shouldBe Some(NumberBet(10))
    }

    "correctly parse a red bet" in {
      chain.parse("r") shouldBe Some(RedBet())
    }

    "correctly parse a black bet" in {
      chain.parse("black") shouldBe Some(BlackBet())
    }

    "correctly parse a first third bet" in {
      chain.parse("1/3") shouldBe Some(FirstThirdBet())
    }

    "correctly parse a second third bet" in {
      chain.parse("2t") shouldBe Some(SecondThirdBet())
    }

    "correctly parse a third third bet" in {
      chain.parse("3/3") shouldBe Some(ThirdThirdBet())
    }

    "correctly parse a first half bet" in {
      chain.parse("1h") shouldBe Some(FirstHalfBet())
    }

    "correctly parse a second half bet" in {
      chain.parse("2/2") shouldBe Some(SecondHalfBet())
    }

    "return None for invalid input" in {
      chain.parse("invalid") shouldBe None
    }

    "return None at the end of the chain" in {
      val parser = new NumberBetParser() // A chain with only one link
      parser.parse("red") shouldBe None
    }
  }
}