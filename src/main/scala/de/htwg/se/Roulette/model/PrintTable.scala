package de.htwg.se.Roulette.model

object PrintTable {
  def printTable(totalWidth: Int, height: Int,randomInt: Option[Int]): String = {
    val bigBoxHeight = height
    val smallBoxHeight = height

    val ho = HexOffset.computeHexOffset(totalWidth, height)
    val top = " " * ho + PrintLine.printLine(totalWidth, 3)+ "\n"
    val hexagon = PrintHex.printHex(totalWidth, height, randomInt)

    val firstRow = PrintBoxRow.printBoxRow(totalWidth, bigBoxHeight, 1, includeBottom = false, rowIndex = 0, offset = ho)
    val middleRows = (1 until 13).map(i =>
      PrintBoxRow.printBoxRow(totalWidth, smallBoxHeight, 3, includeBottom = false, rowIndex = i, offset = ho)
    )
    val bottom = " " * ho + PrintLine.printLine(totalWidth, 3)

    (Seq(top+hexagon, firstRow) ++ middleRows :+ bottom).mkString("\n")
  }
}
