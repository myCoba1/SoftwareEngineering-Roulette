package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class HexOffsetSpec extends AnyWordSpec with Matchers {

  "The HexOffset utility object" when {

    "computing offsets for standard dimensions" should {
      "return the correct value" in {
        val offset = HexOffset.computeHexOffset(totalWidth = 10, height = 5)
        offset should be (3)
      }
    }

    "handling rounding down" should {
      "round correctly" in {
        val offset = HexOffset.computeHexOffset(totalWidth = 7, height = 4)
        offset should be (3)
      }
    }

    "handling rounding up" should {
      "round correctly" in {
        val offset = HexOffset.computeHexOffset(totalWidth = 15, height = 6)
        offset should be (4)
      }
    }

    "enforcing a minimum offset of 2" should {
      "not return a value below 2" in {
        val offset = HexOffset.computeHexOffset(totalWidth = 2, height = 1)
        offset should be (2)

        val offset2 = HexOffset.computeHexOffset(totalWidth = 1, height = 1)
        offset2 should be (2)
      }
    }

    "working with larger inputs" should {
      "compute the correct value" in {
        val offset = HexOffset.computeHexOffset(totalWidth = 50, height = 20)
        offset should be (14)
      }
    }
  }
}
