package de.htwg.se.Roulette.model

object PrintTable {
  def printTable(totalWidth: Int, height: Int, randomInt: Option[Int]): String = {
    new TableBuilder(totalWidth, height, randomInt)
      .buildHexPart()
      .buildTableGridPart()
      .buildSideParts()
      .getResult
  }
}
