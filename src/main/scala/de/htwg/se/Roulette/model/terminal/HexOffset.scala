package de.htwg.se.Roulette.model.terminal

object HexOffset {
  def computeHexOffset(totalWidth: Int, height: Int): Int = {
    // offset = round(totalWidth * height / (totalWidth + height)); min. 2
    math.max(2, ((totalWidth * height).toDouble / (totalWidth + height)).round.toInt)
  }
}
