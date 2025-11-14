package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PrintTableSpec extends AnyWordSpec with Matchers {

  "PrintTable.printTable" when {

    "called with no number" should {
      val totalWidth = 10
      val height = 3
      val output = PrintTable.printTable(totalWidth, height, randomInt = None)
      val lines = output.linesIterator.toSeq

      "return a non-empty string" in {
        output should not be empty
      }

      "have top line with correct offset" in {
        lines.head.trim should startWith("+")
      }

      "contain the hexagon near the top" in {
        // hex lines are right after the top line
        val hexLines = lines.slice(1, 1 + height)
        hexLines.exists(line => line.contains("/") && line.contains("\\")) shouldBe true
      }

      "contain the first row of boxes with correct offset" in {
        val firstBoxLine = lines(1 + height)
        firstBoxLine should include("|")
      }

      "contain middle rows of boxes" in {
        // box rows start after top line + hex + first row
        val boxStart = 1 + height + 1
        val boxLines = lines.drop(boxStart).take(13 * height) // 13 rows, each of height lines
        boxLines.exists(_.contains("|")) shouldBe true
      }

      "have bottom line with correct offset" in {
        lines.last.trim should startWith("+")
        lines.last.trim should endWith("+")
      }
    }

    "called with a number" should {
      val totalWidth = 10
      val height = 3
      val number = 7
      val output = PrintTable.printTable(totalWidth, height, randomInt = Some(number))

      "render the number in the hexagon" in {
        val hexLines = output.linesIterator.toSeq.slice(1, 1 + height * 2) // top + middle + bottom roughly
        val middleStart = height // middle section starts after top slants
        val middleHeight = math.max(height / 2, 1)
        val middleSection = hexLines.slice(middleStart, middleStart + middleHeight)
        middleSection.exists(_.contains(number.toString)) shouldBe true
      }

    }

  }
}
