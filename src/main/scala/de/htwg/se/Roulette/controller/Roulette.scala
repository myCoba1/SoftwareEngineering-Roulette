package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.aview.RouletteRound

object Roulette {
  def main(args: Array[String]): Unit = {
    var continue = true
    while (continue) {
      continue = RouletteRound.rouletteRound()
    }
  }
}