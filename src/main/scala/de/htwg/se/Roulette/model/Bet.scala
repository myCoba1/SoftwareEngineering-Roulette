package de.htwg.se.Roulette.model

trait Bet {
  def isWinningBet(winningNumber: Int): Boolean
}
