package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.*

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
    
    val firstHalfBetParser = new FirstHalfBetParser()
    val secondHalfBetParser = new SecondHalfBetParser()
    
    val evenBetParser = new EvenBetParser()
    val oddBetParser = new OddBetParser()
    
    val lineOneBetParser = new LineOneBetParser()
    val lineTwoBetParser = new LineTwoBetParser()
    val lineThreeBetParser = new LineThreeBetParser()

    numberParser.setNext(redParser).setNext(blackParser)
      .setNext(firstThirdParser).setNext(secondThirdParser).setNext(thirdThirdParser)
      .setNext(firstHalfBetParser).setNext(secondHalfBetParser)
      .setNext(evenBetParser).setNext(oddBetParser)
      .setNext(lineOneBetParser).setNext(lineTwoBetParser).setNext(lineThreeBetParser)
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