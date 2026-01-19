package de.htwg.se.Roulette.model.fileIoComponent

import de.htwg.se.Roulette.model.GameStateInterface

trait FileIOInterface {
  def load: GameStateInterface
  def save(gameState: GameStateInterface): Unit
}