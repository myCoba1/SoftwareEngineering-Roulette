package de.htwg.se.Roulette.model

import de.htwg.se.Roulette.model.terminal.{HexOffset, PrintBoxRow, PrintHex, PrintLine, WordBox}

class TableBuilder(totalWidth: Int, height: Int, randomInt: Option[Int]) {
  private var hexBlock: String = ""
  private var tableBlock: String = ""
  private var thirdsBlock: String = ""
  private var sideBlock: String = ""

  def buildHexPart(): this.type = {
    val ho = HexOffset.computeHexOffset(totalWidth, height)
    val top = " " * ho + PrintLine.printLine(totalWidth, 3) + "\n"
    val hexagon = PrintHex.printHex(totalWidth, height, randomInt)
    this.hexBlock = top + hexagon
    this
  }

  def buildTableGridPart(): this.type = {
    val bigBoxHeight = height
    val smallBoxHeight = height
    val ho = HexOffset.computeHexOffset(totalWidth, height)

    val firstRow =
      PrintBoxRow.printBoxRow(totalWidth, bigBoxHeight, 1, includeBottom = false, rowIndex = 0, offset = ho)

    val middleRows =
      (1 until 13).map(i =>
        PrintBoxRow.printBoxRow(totalWidth, smallBoxHeight, 3, includeBottom = false, rowIndex = i, offset = ho)
      )

    val bottom = " " * ho + PrintLine.printLine(totalWidth, 3)

    this.tableBlock = (Seq(firstRow) ++ middleRows :+ bottom).mkString("\n")
    this
  }

  def buildSideParts(): this.type = {
    val blackBox = WordBox.wordBox("black")
    val redBox = WordBox.wordBox("red")
    val firstHalfBox = WordBox.wordBox("1-18")
    val secondHalfBox = WordBox.wordBox("19-36")

    this.sideBlock = (firstHalfBox ++ Vector("| E |") ++ blackBox ++ redBox ++ Vector("| O |") ++ secondHalfBox).mkString("\n")

    val firstThird = WordBox.wordBox("1st 12")
    val secondThird = WordBox.wordBox("2nd 12")
    val thirdThird = WordBox.wordBox("3rd 12")
    this.thirdsBlock = (firstThird ++ Vector(" ") ++ secondThird ++ Vector(" ") ++ thirdThird).mkString("\n")
    this
  }

  def getResult: String = {
    val tableBlockLines = tableBlock.split("\n").toVector
    val thirdsLines = thirdsBlock.split("\n").toVector
    val sideLines = sideBlock.split("\n").toVector

    val sideOffset = (tableBlockLines.length - sideLines.length) / 2
    val centeredSide = Vector.fill(math.max(0, sideOffset))("") ++ sideLines ++ Vector.fill(math.max(0, tableBlockLines.length - sideLines.length - sideOffset))("")

    val thirdsOffset = (tableBlockLines.length - thirdsLines.length) / 2 + 1
    val centeredThirds = Vector.fill(math.max(0, thirdsOffset))("") ++ thirdsLines ++ Vector.fill(math.max(0, tableBlockLines.length - thirdsLines.length - thirdsOffset))("")

    val maxLines = math.max(tableBlockLines.length, math.max(centeredSide.length, centeredThirds.length))
    val paddedTable = tableBlockLines.padTo(maxLines, "")
    val paddedThirds = centeredThirds.padTo(maxLines, "")
    val paddedSide = centeredSide.padTo(maxLines, "")

    val leftWidth = paddedTable.map(_.length).maxOption.getOrElse(0)
    val midWidth = paddedThirds.map(_.length).maxOption.getOrElse(0)

    val tableWithBothSides = (0 until maxLines).map { i =>
      val left = paddedTable(i)
      val mid = paddedThirds(i)
      val right = paddedSide(i)
      val leftPadded = left + " " * (leftWidth - left.length)
      val midPadded = if (mid.nonEmpty) mid + " " * (midWidth - mid.length) else " " * midWidth
      val withMid = if (midWidth > 0) leftPadded + "  " + midPadded else leftPadded
      if (right.nonEmpty) withMid + "  " + right else withMid
    }.mkString("\n")

    (hexBlock.split("\n").toVector ++ tableWithBothSides.split("\n").toVector).mkString("\n")
  }
}