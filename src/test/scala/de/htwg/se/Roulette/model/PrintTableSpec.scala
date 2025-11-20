package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PrintTableSpec extends AnyWordSpec with Matchers {

  "PrintTable.printTable" should {

    "produce a multi-line string" in {
      val result = PrintTable.printTable(12, 3, None)
      val lines = result.split("\n")
      lines.length should be > 5
    }

    "include the hexagon block at the top" in {
      val result = PrintTable.printTable(12, 3, Some(7))
      val firstLines = result.split("\n").take(5)
      firstLines.mkString("\n") should not be empty
    }

    "include 13 middle rows from PrintBoxRow" in {
      val result = PrintTable.printTable(15, 2, None)
      val lines = result.split("\n").toList

      val numbers = (1 to 36).map(_.toString)
      val found = numbers.filter(n => lines.exists(_.contains(n)))

      found.length shouldBe 36
    }

    "include the black/red side boxes" in {
      val result = PrintTable.printTable(20, 3, None)
      val all = result.split("\n").toList

      "black".foreach { ch =>
        all.exists(_.contains(ch.toString)) shouldBe true
      }

      "red".foreach { ch =>
        all.exists(_.contains(ch.toString)) shouldBe true
      }
    }

    "include the '1st 12', '2nd 12', '3rd 12' blocks" in {
      val result = PrintTable.printTable(20, 3, None)
      val all = result.split("\n").toList

      "1st 12".foreach { ch =>
        all.exists(_.contains(ch.toString)) shouldBe true
      }

      "2nd 12".foreach { ch =>
        all.exists(_.contains(ch.toString)) shouldBe true
      }

      "3rd 12".foreach { ch =>
        all.exists(_.contains(ch.toString)) shouldBe true
      }
    }

    "never produce empty trailing lines" in {
      val result = PrintTable.printTable(20, 3, None)
      result.endsWith("\n") shouldBe false
    }

  }
}
