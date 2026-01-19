package de.htwg.se.Roulette.model.modelImpl

import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.Bet

case class GameState(winningNumber: Int, bets: List[Bet] = List.empty, balance: Int = 100) extends GameStateInterface