package de.htwg.se.Roulette.model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class HexOffsetSpec extends AnyFunSpec with Matchers {

  // Use 'describe' instead of string block for main context
  describe("The HexOffset utility object") {

    // individual it instead of in
    it("should calculate the correct offset for standard dimensions") {
      // round( (10 * 5) / (10 + 5) ) = round(50 / 15) = round(3.33) = 3
      val offset = HexOffset.computeHexOffset(totalWidth = 10, height = 5)
      offset should be (3)
    }

    it("should handle rounding correctly (rounding down)") {
      // round( (7 * 4) / (7 + 4) ) = round(28 / 11) = round(2.54) = 3
      val offset = HexOffset.computeHexOffset(totalWidth = 7, height = 4)
      offset should be (3)
    }

    it("should handle rounding correctly (rounding up)") {
      // round( (15 * 6) / (15 + 6) ) = round(90 / 21) = round(4.28) = 4
      val offset = HexOffset.computeHexOffset(totalWidth = 15, height = 6)
      offset should be (4)
    }

    it("should enforce a minimum offset of 2") {
      // result is 1, but should be capped at 2
      val offset = HexOffset.computeHexOffset(totalWidth = 2, height = 1)
      offset should be (2)

      //  low-value check
      val offset2 = HexOffset.computeHexOffset(totalWidth = 1, height = 1)
      offset2 should be (2)
    }

    it("should work correctly with larger input values") {
      // round( (50 * 20) / (50 + 20) ) = round(1000 / 70) = round(14.28) = 14
      val offset = HexOffset.computeHexOffset(totalWidth = 50, height = 20)
      offset should be (14)
    }
  }
}