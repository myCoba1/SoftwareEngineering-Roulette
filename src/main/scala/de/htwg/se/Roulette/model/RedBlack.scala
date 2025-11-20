package de.htwg.se.Roulette.model

object RedBlack {
  private val reds = Set(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36)

  def colorOf(n: Int): Char = n match {
    case 0 => 'G'
    case x if reds.contains(x) => 'R'
    case _ => 'B'
  }
}
