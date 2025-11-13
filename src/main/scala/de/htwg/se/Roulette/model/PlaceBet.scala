package de.htwg.se.Roulette.model

object PlaceBet {
  def placeBet(randomInt: Int): Boolean = {
    print("Place a Bet (100 to exit): ")
    val bet = scala.io.StdIn.readInt()
    if (bet == 100) false            // stop
    else {
      if (bet == randomInt) println("Win") else println("Lose")
      true                            // continue
    }
  }
}
