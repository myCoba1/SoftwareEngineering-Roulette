package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PrintHexSpec extends AnyWordSpec with Matchers {

  "PrintHex.printHex" when {

    "called with a small hex and no number" should {
      val output = PrintHex.printHex(totalWidth = 4, height = 2, randomInt = None)
      val lines = output.linesIterator.toSeq

      "generate the correct number of lines" in {
        lines.size shouldBe (2 + 1 + 2) // top + middle + bottom
      }

      "have empty middle" in {
        val middleLine = lines(2)
        middleLine should startWith("|")
        middleLine should endWith("|")
        middleLine.trim.stripPrefix("|").stripSuffix("|").trim shouldBe empty
      }

      "render top correctly" in {
        lines.take(2).foreach(line => line should startWith(" "))
      }

      "render bottom correctly" in {
        lines.takeRight(2).foreach { line =>
          line.trim should startWith("\\")
          line.trim should endWith("/")
        }
      }
    }

    "called with a number that fits" should {
      val output = PrintHex.printHex(totalWidth = 4, height = 2, randomInt = Some(5))
      val lines = output.linesIterator.toSeq
      val maxInner = 4 + 2 + 2*2

      "display the number in the middle row" in {
        val middleLine = lines(2)
        middleLine should include ("5")
      }

      "pad the number correctly" in {
        val middleLine = lines(2)
        middleLine.length shouldBe (maxInner + 2) // plus 2 for the "|" bars
      }
    }

    "called with a number longer than max width" should {
      val output = PrintHex.printHex(totalWidth = 4, height = 2, randomInt = Some(99999))
      val lines = output.linesIterator.toSeq
      val maxInner = 4 + 2 + 2*2

      "truncate the number" in {
        val middleLine = lines(2)
        middleLine.length shouldBe (maxInner + 2)
      }
    }

    "called with a larger hex" should {
      val output = PrintHex.printHex(totalWidth = 6, height = 4, randomInt = Some(12))
      val lines = output.linesIterator.toSeq
      val middleHeight = math.max(4 / 2, 1)
      val midIndex = middleHeight / 2

      "generate the correct total lines" in {
        lines.size shouldBe (4 + middleHeight + 4)
      }

      "center the number in the middle" in {
        val middleLine = lines(4 + midIndex)
        middleLine should include ("12")
      }

      "ensure top and bottom are symmetric" in {
        val top = lines.take(4)
        val bottom = lines.takeRight(4)
        val topInner = top.map(_.trim.drop(1).dropRight(1))
        val bottomInner = bottom.reverse.map(_.trim.drop(1).dropRight(1))
        topInner shouldBe bottomInner
      }
    }

    "handle height = 1 correctly" in {
      val output = PrintHex.printHex(totalWidth = 3, height = 1, randomInt = Some(7))
      val lines = output.linesIterator.toSeq
      lines.size shouldBe (1 + math.max(1/2, 1) + 1)
      val middleLine = lines(1)
      middleLine should include ("7")
      middleLine should startWith("|")
      middleLine should endWith("|")
    }

    "handle None for randomInt" in {
      val output = PrintHex.printHex(totalWidth = 5, height = 3, randomInt = None)
      val lines = output.linesIterator.toSeq

      val hexHeight = 3
      val middleHeight = math.max(hexHeight / 2, 1)
      val midIndex = middleHeight / 2

      val middleLine = lines(hexHeight + midIndex) // correct index for middle row

      val content = middleLine.stripPrefix("|").stripSuffix("|").trim
      content shouldBe ""
    }




  }

}
