package de.htwg.se.Roulette.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class RedBlackSpec extends AnyWordSpec with Matchers {

  "RedBlack.colorOf" should {

    "return G for 0" in {
      RedBlack.colorOf(0) shouldBe 'G'
    }

    "return R for all red numbers" in {
      val reds = Set(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36)
      reds.foreach(n => RedBlack.colorOf(n) shouldBe 'R')
    }

    "return B for all black numbers" in {
      val all = 0 to 36
      val reds = Set(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36)

      val blacks = all.filterNot(n => n == 0 || reds.contains(n))
      blacks.foreach(n => RedBlack.colorOf(n) shouldBe 'B')
    }

    "not fail for numbers outside 0–36 (still returns B)" in {
      RedBlack.colorOf(100) shouldBe 'B'
      RedBlack.colorOf(-5) shouldBe 'B'
    }

  }
}
