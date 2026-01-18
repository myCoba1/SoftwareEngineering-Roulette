package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.Bet

object TwoToOne {
  val lineOne: Set[Int] = (1 to 36 by 3).toSet
  val lineTwo: Set[Int] = (2 to 36 by 3).toSet
  val lineThree: Set[Int] = (3 to 36 by 3).toSet

  def lineOf(n: Int): String = n match {
    case x if lineOne.contains(x) => "1. 2-1"
    case x if lineTwo.contains(x) => "2. 2-1"
    case x if lineThree.contains(x) => "3. 2-1"
    case _ => "None"
  }
}
case class LineOneBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    TwoToOne.lineOne.contains(winningNumber)
  }
  override def toString: String = "1. 2-1"
}

case class LineTwoBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    TwoToOne.lineTwo.contains(winningNumber)
    }
  override def toString: String = "2. 2-1"
}

case class LineThreeBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    TwoToOne.lineThree.contains(winningNumber)
  }
  override def toString: String = "3. 2-1"
}