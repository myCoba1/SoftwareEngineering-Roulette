object Roulette {
  def main(args: Array[String]): Unit = {
    print(printTable(23, 5))  //(11*n+(n-1),n*2-1)
  }

  private def printTable(width: Int, height: Int): String = {
    val bigBoxHeight = height
    val smallBoxHeight = height

    val firstRow = printBoxRow(width, bigBoxHeight, 1, includeBottom = false, rowIndex = 0)
    val middleRows = (1 until 13).map(i =>
      printBoxRow(width, smallBoxHeight, 3, includeBottom = false, rowIndex = i)
    )
    val bottom = printBottomLine(width, 3)

    (firstRow +: middleRows :+ bottom).mkString("")
  }

  private def printBoxRow(totalWidth: Int, boxHeight: Int, boxesPerRow: Int, includeBottom: Boolean, rowIndex: Int): String = {
    val boxWidth = totalWidth / boxesPerRow
    val horizontal = ("+" + "-" * boxWidth) * boxesPerRow + "+"
    val middle = ("|" + " " * boxWidth) * boxesPerRow + "|"

    val startNum = (boxesPerRow * (rowIndex - 1)) + 1
    val numbers = (startNum until startNum + boxesPerRow).toList

    val numberLine = numbers.map { n =>
      val str = n.toString
      val padLeft = (boxWidth - str.length) / 2
      val padRight = boxWidth - str.length - padLeft
      "|" + " " * padLeft + str + " " * padRight
    }.mkString("") + "|"

    val lines = (0 until boxHeight).map { i =>
      if (i == boxHeight / 2) numberLine else middle
    }

    val bottom = if (includeBottom) printBottomLine(totalWidth, boxesPerRow) else ""
    (horizontal +: lines :+ bottom).mkString("\n")
  }

  private def printBottomLine(totalWidth: Int, boxesPerRow: Int): String = {
    val boxWidth = totalWidth / boxesPerRow
    ("+" + "-" * boxWidth) * boxesPerRow + "+"
  }
}
