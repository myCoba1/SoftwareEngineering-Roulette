package de.htwg.se.Roulette.model.terminal

object PrintLine {
  def printLine(totalWidth: Int, boxesPerRow: Int): String = {
    val boxWidth = totalWidth / boxesPerRow
    ("+" + "-" * boxWidth) * boxesPerRow + "+"
  }
}
