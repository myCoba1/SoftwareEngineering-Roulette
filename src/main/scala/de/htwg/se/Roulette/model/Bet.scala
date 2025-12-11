package de.htwg.se.Roulette.model

trait Bet {
  def isWinningBet(winningNumber: Int): Boolean
}

object Bet {
  private val chain: BetParser = {
    val numberParser = new NumberBetParser()
    val redParser = new RedBetParser()
    val blackParser = new BlackBetParser()
    val firstThirdParser = new FirstThirdBetParser()
    val secondThirdParser = new SecondThirdBetParser()
    val thirdThirdParser = new ThirdThirdBetParser()

    numberParser.setNext(redParser).setNext(blackParser).setNext(firstThirdParser).setNext(secondThirdParser).setNext(thirdThirdParser)
    numberParser
  }

  def apply(input: String): List[Bet] = {
    input.trim.split("\\s+").flatMap(chain.parse).toList
  }
}

case class NumberBet(number: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = number == winningNumber
  override def toString: String = number.toString
}