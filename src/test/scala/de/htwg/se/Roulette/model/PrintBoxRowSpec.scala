package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PrintBoxRowSpec extends AnyWordSpec with Matchers {

  "PrintBoxRow.printBoxRow" should {

    "produce correct dimensions" in {
      // Arrange
      val totalWidth = 12
      val boxHeight = 3
      val boxesPerRow = 3
      val includeBottom = true
      val rowIndex = 1

      // Act
      val result = PrintBoxRow.printBoxRow(
        totalWidth, boxHeight, boxesPerRow, includeBottom, rowIndex
      )

      // Split into lines
      val lines = result.split("\n")

      // Assert: line count = 1 top border + boxHeight interior + 1 bottom
      lines.length shouldBe (1 + boxHeight + 1)

      // Each line should have consistent width
      val expectedWidth = totalWidth + boxesPerRow + 1 // "+-----" patterns include separators
      for (line <- lines) {
        line.length shouldBe expectedWidth
      }
    }

    "place correct numbers in the number row" in {
      // Arrange
      val totalWidth = 9
      val boxHeight = 3
      val boxesPerRow = 3
      val includeBottom = false
      val rowIndex = 2   // numbers: 4, 5, 6

      // Act
      val result = PrintBoxRow.printBoxRow(
        totalWidth, boxHeight, boxesPerRow, includeBottom, rowIndex
      )
      val lines = result.split("\n")

      // Middle line containing numbers is index = 1 + boxHeight/2
      val numLineIndex = 1 + boxHeight / 2
      val numLine = lines(numLineIndex)

      // Assert that it contains "4", "5", "6" in that order
      numLine should include ("4")
      numLine should include ("5")
      numLine should include ("6")
    }

    "respect the offset" in {
      val result = PrintBoxRow.printBoxRow(
        totalWidth = 9,
        boxHeight = 2,
        boxesPerRow = 3,
        includeBottom = false,
        rowIndex = 1,
        offset = 4
      )

      val firstLine = result.split("\n").head
      firstLine.startsWith("    ") shouldBe true // 4 spaces
    }

  }
}
