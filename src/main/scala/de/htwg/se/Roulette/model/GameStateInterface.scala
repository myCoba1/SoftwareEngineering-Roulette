package de.htwg.se.Roulette.model

import de.htwg.se.Roulette.model.bets.Bet

trait GameStateInterface {
  def winningNumber: Int
  def bets: List[Bet]
  def balance: Int
}