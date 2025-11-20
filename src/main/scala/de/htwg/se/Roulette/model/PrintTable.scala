package de.htwg.se.Roulette.model

object PrintTable {
  def printTable(totalWidth: Int, height: Int, randomInt: Option[Int]): String = {
    val bigBoxHeight = height
    val smallBoxHeight = height

    val ho = HexOffset.computeHexOffset(totalWidth, height)
    val top = " " * ho + PrintLine.printLine(totalWidth, 3) + "\n"
    val hexagon = PrintHex.printHex(totalWidth, height, randomInt)

    val firstRow =
      PrintBoxRow.printBoxRow(totalWidth, bigBoxHeight, 1, includeBottom = false, rowIndex = 0, offset = ho)

    val middleRows =
      (1 until 13).map(i =>
        PrintBoxRow.printBoxRow(totalWidth, smallBoxHeight, 3, includeBottom = false, rowIndex = i, offset = ho)
      )

    val bottom = " " * ho + PrintLine.printLine(totalWidth, 3)

    val hexBlock = top + hexagon

    val tableBlockString =
      (Seq(firstRow) ++ middleRows :+ bottom).mkString("\n")

    val tableBlockLines = tableBlockString.split("\n").toVector

    val blackBox = WordBox.wordBox("black")
    val redBox = WordBox.wordBox("red")
    val side = blackBox ++ Vector(" ") ++ redBox

    val sideOffset = (tableBlockLines.length - side.length) / 2

    val centeredSide =
      Vector.fill(math.max(0, sideOffset))("") ++
        side ++
        Vector.fill(math.max(0, tableBlockLines.length - side.length - sideOffset))("")

    val firstThird = WordBox.wordBox("1st 12")
    val secondThird = WordBox.wordBox("2nd 12")
    val thirdThird = WordBox.wordBox("3rd 12")

    val thirds = firstThird ++ Vector(" ") ++ secondThird ++ Vector(" ") ++ thirdThird
    val thirdsOffset = (tableBlockLines.length - thirds.length) / 2
    val centeredThirds =
      Vector.fill(math.max(0, thirdsOffset))("") ++
        thirds ++
        Vector.fill(math.max(0, tableBlockLines.length - thirds.length - thirdsOffset))("")

    val maxLines = math.max(tableBlockLines.length, math.max(centeredSide.length, centeredThirds.length))
    val paddedTable = tableBlockLines.padTo(maxLines, "")
    val paddedThirds = centeredThirds.padTo(maxLines, "")
    val paddedSide   = centeredSide.padTo(maxLines, "")

    val leftWidth = paddedTable.map(_.length).maxOption.getOrElse(0)
    val midWidth = paddedThirds.map(_.length).maxOption.getOrElse(0)

    val tableWithBothSides = (0 until maxLines).map { i =>
      val left = paddedTable(i)
      val mid = paddedThirds(i)
      val right = paddedSide(i)

      val leftPadded = left + " " * (leftWidth - left.length)
      val midPadded =
        if (mid.nonEmpty) mid + " " * (midWidth - mid.length)
        else " " * midWidth

      val withMid = if (midWidth > 0) leftPadded + "  " + midPadded else leftPadded

      if (right.nonEmpty) withMid + "  " + right else withMid
    }.mkString("\n")

    val hexLines = hexBlock.split("\n").toVector
    (hexLines ++ tableWithBothSides.split("\n").toVector).mkString("\n")
  }
}
