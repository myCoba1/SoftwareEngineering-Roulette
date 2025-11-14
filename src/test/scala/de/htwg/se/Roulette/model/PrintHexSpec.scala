package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PrintHexSpec extends AnyWordSpec with Matchers {

  "PrintHex.printHex" when {

    "called with a small hex and no number" should {
      val output = PrintHex.printHex(totalWidth = 4, height = 2, randomInt = None)

      "generate the correct number of lines" in {
        // total lines = top + middle + bottom = height + max(1, height/2) + height
        output.linesIterator.size should be (2 + 1 + 2)
      }

      "have empty middle" in {
        val middleLine = output.linesIterator.toSeq.drop(2).head
        middleLine should startWith("|")
        middleLine should endWith("|")
        middleLine.trim.stripPrefix("|").stripSuffix("|").trim shouldBe empty
      }

      "render top correctly" in {
        output.linesIterator.take(2).toList.foreach(line => line should startWith(" "))
      }

      "render bottom correctly" in {
        val lines = output.linesIterator.toSeq
        lines.takeRight(2).foreach { line =>
          line.trim should startWith("\\")
          line.trim should endWith("/")
        }
      }
    }

    "called with a number that fits" should {
      val output = PrintHex.printHex(totalWidth = 4, height = 2, randomInt = Some(5))

      "display the number in the middle row" in {
        val middleLine = output.linesIterator.drop(2).take(1).mkString
        middleLine should include ("5")
      }

      "pad the number correctly" in {
        val middleLine = output.linesIterator.drop(2).take(1).mkString
        middleLine.length should be (4 + 2 + 2*2 + 2)  // maxInner + 2 for vert bars
      }
    }

    "called with a number longer than max width" should {
      val output = PrintHex.printHex(totalWidth = 4, height = 2, randomInt = Some(99999))

      "truncate the number" in {
        val middleLine = output.linesIterator.slice(2, 3).mkString
        middleLine.length should be (4 + 2 + 2*2 + 2)
      }
    }

    "called with a larger hex" should {
      val output = PrintHex.printHex(totalWidth = 6, height = 4, randomInt = Some(12))

      "generate the correct total lines" in {
        output.linesIterator.size should be (4 + 2 + 4)  // top + mid + bot
      }

      "center the number in the middle" in {
        val middleLine = output.linesIterator.slice(4, 6).mkString
        middleLine should include ("12")
      }
    }

  }
}
