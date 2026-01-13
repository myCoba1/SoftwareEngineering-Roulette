package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.Bet

case class GameState(winningNumber: Int, bets: List[Bet] = List.empty)