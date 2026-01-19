package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.GameStateInterface

sealed trait ControllerEvent
case class NewRound(gameState: GameStateInterface) extends ControllerEvent
case class BetPlaced(gameState: GameStateInterface) extends ControllerEvent
case object BetUndone extends ControllerEvent