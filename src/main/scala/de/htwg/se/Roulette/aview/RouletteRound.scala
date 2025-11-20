package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.model.PrintTable

object RouletteRound {
  def rouletteRound(): Boolean = {
    val randomInt = scala.util.Random.nextInt(36)
    val emptyTable = PrintTable.printTable(23, 2, None) // empty table
    val trueTable = PrintTable.printTable(23, 2, Some(randomInt)) // table with number
    println(emptyTable)
    val bet = PlaceBet.placeBet(randomInt)
    if (bet) {
      println(trueTable)
    }
    val continue = scala.io.StdIn.readLine("Play another round? (y/n): ")
    if (continue.toLowerCase == "y")
      true
    else
      false
  }
}
