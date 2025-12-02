package de.htwg.se.Roulette.model

object BetFactory {
  def getBets(input: String): List[Bet] = {
    input.trim.split("\\s+").flatMap(parseBet).toList
  }

  private def parseBet(betString: String): Option[Bet] = {
    betString.toLowerCase match {
      case "red" | "r" => Some(RedBet())
      case "black" | "b" => Some(BlackBet())
      case "1/3" | "1,3" => Some(FirstThirdBet())
      case "2/3" | "2,3" => Some(SecondThirdBet())
      case "3/3" | "3,3" => Some(ThirdThirdBet())
      case _ =>
        try {
          val num = betString.toInt
          if (num >= 0 && num <= 36) Some(NumberBet(num))
          else None
        } catch {
          case _: NumberFormatException => None
        }
    }
  }
}

case class NumberBet(number: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = number == winningNumber
  override def toString: String = number.toString
}