package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class WordBoxSpec extends AnyWordSpec with Matchers {

  "WordBox.wordBox" should {

    "produce a top and bottom border" in {
      val box = WordBox.wordBox("A", innerWidth = 3)

      box.head shouldBe "+---+"
      box.last shouldBe "+---+"
    }

    "place one character per line for a single-letter word" in {
      val box = WordBox.wordBox("A", innerWidth = 3)

      box.length shouldBe 3
      box(1) shouldBe "| A |"
    }

    "place each character of a multi-letter word on its own line" in {
      val box = WordBox.wordBox("CAT", innerWidth = 3)

      box.length shouldBe 5
      box(1) shouldBe "| C |"
      box(2) shouldBe "| A |"
      box(3) shouldBe "| T |"
    }

    "center characters correctly when innerWidth is even" in {
      val box = WordBox.wordBox("X", innerWidth = 4)

      // Correct expected line: padLeft=1, padRight=2
      box(1) shouldBe "| X  |"
    }

    "handle empty word" in {
      val box = WordBox.wordBox("", innerWidth = 3)

      box shouldBe Vector(
        "+---+",
        "+---+"
      )
    }

  }
}
