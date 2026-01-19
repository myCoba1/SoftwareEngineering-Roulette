package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.Bet

object RedBlack {
  val reds: Set[Int] = Set(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36)
  val blacks: Set[Int] = Set(2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35)

  def colorOf(n: Int): Char = n match {
    case 0 => 'G'
    case x if reds.contains(x) => 'R'
    case _ => 'B' // Assuming all other non-zero numbers are black
  }
}

case class RedBet(amount: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    RedBlack.reds.contains(winningNumber)
  }
  override def multiplier: Int = 1
  override def toString: String = s"Red ($amount)"
}

case class BlackBet(amount: Int) extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    RedBlack.blacks.contains(winningNumber)
  }
  override def multiplier: Int = 1
  override def toString: String = s"Black ($amount)"
}
