package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.aview.{RouletteRound, ConsoleObserver}

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
