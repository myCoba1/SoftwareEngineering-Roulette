package de.htwg.se.Roulette

import de.htwg.se.Roulette.aview.{ConsoleObserver, RouletteRound}
import de.htwg.se.Roulette.controller.GameController

object Roulette {
  def main(args: Array[String]): Unit = {
    val controller = new GameController()
    new ConsoleObserver(controller)

    var cont = true
    while (cont) {
      cont = RouletteRound.rouletteRound(controller)
    }
  }
}
