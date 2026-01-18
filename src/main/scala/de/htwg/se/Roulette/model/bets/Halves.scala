package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.Bet

object Halves {
  val firstHalf: Set[Int] = (1 to 18).toSet
  val secondHalf: Set[Int] = (19 to 36).toSet

  def halfOf(n: Int): String = n match {
    case x if firstHalf.contains(x) => "1/2"
    case x if secondHalf.contains(x) => "2/2"
    case _ => "None"
  }
}
case class FirstHalfBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    Halves.firstHalf.contains(winningNumber)
  }
  override def toString: String = "1 to 18"
}

case class SecondHalfBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    Halves.secondHalf.contains(winningNumber)
  }
  override def toString: String = "19 to 36"
}