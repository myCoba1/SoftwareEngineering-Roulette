package de.htwg.se.Roulette

import de.htwg.se.Roulette.aview._
import de.htwg.se.Roulette.controller.GameController

object Roulette {
  def main(args: Array[String]): Unit = {
    val controller = new GameController()
    new ConsoleObserver(controller)
    controller.startRound()

    var continue = true
    while (continue) {
      RouletteRound.rouletteRound(controller) match {
        case Continue => controller.startRound()
        case Undo =>
        case Quit => continue = false
      }
    }
  }
}
