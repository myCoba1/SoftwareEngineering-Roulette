package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.Bet

object EvenOdd {
  val even: Set[Int] = (2 to 36 by 2).toSet
  val odd: Set[Int] = (1 to 36 by 2).toSet

  def evenOr(n: Int): String = n match {
    case x if even.contains(x) => "even"
    case x if odd.contains(x) => "odd"
    case _ => "None"
  }
}
case class EvenBet(amount: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    EvenOdd.even.contains(winningNumber)
  }
  override def multiplier: Int = 1
  override def toString: String = s"Even ($amount)"
}

case class OddBet(amount: Int) extends Bet{
  override def isWinningBet(winningNumber: Int): Boolean = {
    EvenOdd.odd.contains(winningNumber)
  }
  override def multiplier: Int = 1
  override def toString: String = s"Odd ($amount)"
}