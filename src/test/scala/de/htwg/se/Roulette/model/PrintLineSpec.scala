package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PrintLineSpec extends AnyWordSpec with Matchers {

  "PrintLine.printLine" when {

    "called with totalWidth divisible by boxesPerRow" should {
      val line = PrintLine.printLine(totalWidth = 10, boxesPerRow = 5)

      "return a string with correct box count" in {
        line.count(_ == '+') should be (6) // 5 boxes + 1 ending '+'
      }

      "return a string with correct total length" in {
        line.length should be (10 + 6) // 10 dashes + 6 '+'
      }

      "have correct pattern" in {
        line shouldBe "+--+--+--+--+--+"
      }
    }

    "called with totalWidth not divisible by boxesPerRow" should {
      val line = PrintLine.printLine(totalWidth = 7, boxesPerRow = 3)

      "return a string with correct box count" in {
        line.count(_ == '+') should be (4) // 3 boxes + 1 ending +
      }

      "return a string of expected length" in {
        line.length should be (7 / 3 * 3 + 4) // integer division for width per box + 4 '+'
      }
    }

    "called with one box" should {
      val line = PrintLine.printLine(totalWidth = 5, boxesPerRow = 1)

      "return a string starting and ending with +" in {
        line should startWith("+")
        line should endWith("+")
      }

      "return a string of correct length" in {
        line.length should be (5 + 2) // 5 - + 2 +
      }
    }

  }
}
