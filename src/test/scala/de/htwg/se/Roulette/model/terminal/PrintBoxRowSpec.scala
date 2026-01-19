package de.htwg.se.Roulette.model.terminal

import de.htwg.se.Roulette.model.terminal.PrintBoxRow
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PrintBoxRowSpec extends AnyWordSpec with Matchers {

  "PrintBoxRow.printBoxRow" should {

    "produce correct dimensions" in {
      val totalWidth = 12
      val boxHeight = 3
      val boxesPerRow = 3
      val includeBottom = true
      val rowIndex = 1

      val result = PrintBoxRow.printBoxRow(totalWidth, boxHeight, boxesPerRow, includeBottom, rowIndex)
      val lines = result.split("\n")

      lines.length shouldBe (1 + boxHeight + 1)
      val expectedWidth = totalWidth + boxesPerRow + 1
      for (line <- lines) {
        line.length shouldBe expectedWidth
      }
    }

    "place correct numbers in the number row" in {
      val totalWidth = 9
      val boxHeight = 3
      val boxesPerRow = 3
      val includeBottom = false
      val rowIndex = 2 // numbers: 4,5,6

      val result = PrintBoxRow.printBoxRow(totalWidth, boxHeight, boxesPerRow, includeBottom, rowIndex)
      val lines = result.split("\n")
      val numLineIndex = 1 + boxHeight / 2
      val numLine = lines(numLineIndex)

      numLine should include ("4")
      numLine should include ("5")
      numLine should include ("6")
    }

    "respect the offset" in {
      val result = PrintBoxRow.printBoxRow(9, 2, 3, includeBottom = false, rowIndex = 1, offset = 4)
      val firstLine = result.split("\n").head
      firstLine.startsWith("    ") shouldBe true
    }

    "handle includeBottom false correctly" in {
      val result = PrintBoxRow.printBoxRow(9, 2, 3, includeBottom = false, rowIndex = 1)
      val lines = result.split("\n")
      lines.last.startsWith("+") shouldBe false // bottom line not included
    }

    "handle single box per row" in {
      val result = PrintBoxRow.printBoxRow(5, 2, 1, includeBottom = true, rowIndex = 1)
      val lines = result.split("\n")
      lines.length shouldBe 1 + 2 + 1
      lines.head should startWith("+")
    }

    "handle boxWidth smaller than number length gracefully" in {
    val result = PrintBoxRow.printBoxRow(3, 2, 3, includeBottom = false, rowIndex = 10)
    val lines = result.split("\n")
    val numLineIndex = 1 + 2 / 2
    val numLine = lines(numLineIndex)

    // Only check that color codes exist
    numLine should include ("B")
    numLine should include ("R")
  }

    "maintain consistent row width regardless of rowIndex" in {
      val widths = (1 to 5).map { rowIndex =>
        val result = PrintBoxRow.printBoxRow(12, 3, 3, includeBottom = true, rowIndex)
        result.split("\n").map(_.length).distinct
      }
      widths.foreach(_.length shouldBe 1) // all lines same width
    }

  }
}
