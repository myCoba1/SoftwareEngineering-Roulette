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
case class LineOneBet(amount: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    TwoToOne.lineOne.contains(winningNumber)
  }
  override def multiplier: Int = 2
  override def toString: String = s"1. 2-1 ($amount)"
}

case class LineTwoBet(amount: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    TwoToOne.lineTwo.contains(winningNumber)
    }
  override def multiplier: Int = 2
  override def toString: String = s"2. 2-1 ($amount)"
}

case class LineThreeBet(amount: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    TwoToOne.lineThree.contains(winningNumber)
  }
  override def multiplier: Int = 2
  override def toString: String = s"3. 2-1 ($amount)"
}