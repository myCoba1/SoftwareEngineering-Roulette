package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.Bet
import de.htwg.se.Roulette.util.Observable

import scala.util.Try

trait ControllerInterface extends Observable[ControllerEvent] {
  def startRound(): Unit
  def undo(): Try[Unit]
  def placeBet(bets: List[Bet]): Unit
  def gameState: Option[GameStateInterface]
  def save(): Unit
  def load(): Unit
}