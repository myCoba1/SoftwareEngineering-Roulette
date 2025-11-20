package de.htwg.se.Roulette.model

object WordBox {
  def wordBox(word: String, innerWidth: Int = 3): Vector[String] = {
    val border = "+" + "-" * innerWidth + "+"
    val letterLines = word.toCharArray.toVector.map { ch =>
      val chStr = ch.toString
      val padLeft = (innerWidth - chStr.length) / 2
      val padRight = innerWidth - chStr.length - padLeft
      "|" + " " * padLeft + chStr + " " * padRight + "|"
    }
    Vector(border) ++ letterLines ++ Vector(border)
  }
}
