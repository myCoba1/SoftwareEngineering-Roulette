package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.model.RedBlack
import de.htwg.se.Roulette.model.Thirds

object PlaceBet {
  def placeBet(randomInt: Int): Boolean = {
    def readInput(): Option[Either[Int,Char]] = {
      print("Place a Bet (number 0-36, or color R | B, or third 1/3 | 2/3 | 3/3: ")
      val line = scala.io.StdIn.readLine()
      if (line == null) return None
      val t = line.trim

      scala.util.Try(t.toInt).toOption match {
        case Some(n) if n >= 0 && n <= 36 => Some(Left(n))
        case _ =>
          t.toLowerCase match {
            case "r" | "red"   => Some(Right('R'))
            case "b" | "black" => Some(Right('B'))
            case "1,3" | "1/3"   => Some(Right('1'))
            case "2,3" | "2/3"   => Some(Right('2'))
            case "3,3" | "3/3"   => Some(Right('3'))
            case _ =>
              println("Invalid input. Please enter a number 0-36, R/B, or 1/3, 2/3, 3/3: ")
              None
          }
      }
    }

    var result: Option[Either[Int,Char]] = None
    while (result.isEmpty) {
      result = readInput()
    }

    result.get match {
      case Left(n) =>
        if (n == randomInt) println("Win") else println("Lose")

      case Right(t) if t == '1' || t == '2' || t == '3' =>
        val actualThird = Thirds.thirdOf(randomInt)
        if (actualThird == s"$t/3") println("Win") else println("Lose")

      case Right(c) =>
        val actualColor = RedBlack.colorOf(randomInt) // 'R', 'B' or 'G'
        if (actualColor == c) println("Win") else println("Lose")
    }

    true
  }
}
