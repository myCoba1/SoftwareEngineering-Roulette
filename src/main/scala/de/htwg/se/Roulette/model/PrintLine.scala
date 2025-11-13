package de.htwg.se.Roulette.model

object PrintLine {
  def printLine(totalWidth: Int, boxesPerRow: Int): String = {
    val boxWidth = totalWidth / boxesPerRow
    ("+" + "-" * boxWidth) * boxesPerRow + "+"
  }
}
