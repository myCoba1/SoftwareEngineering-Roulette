package de.htwg.se.Roulette.model

object RouletteRound {
  def rouletteRound(): Boolean = {
    val randomInt = scala.util.Random.nextInt(36)
    println(PrintTable.printTable(11, 1, None)) // empty table
    val continue = PlaceBet.placeBet(randomInt)
    if (continue) {
      println(PrintTable.printTable(11, 1, Some(randomInt)))  //  random int
    }

    continue
  }
}
