package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

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
}
