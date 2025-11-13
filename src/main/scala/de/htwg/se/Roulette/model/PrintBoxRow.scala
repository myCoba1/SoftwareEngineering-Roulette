package de.htwg.se.Roulette.model

object PrintBoxRow {
  def printBoxRow(totalWidth: Int, boxHeight: Int, boxesPerRow: Int, includeBottom: Boolean,
                          rowIndex: Int, offset: Int = 0): String = {
    val boxWidth = totalWidth / boxesPerRow
    val horizontal = " " * offset + (("+" + "-" * boxWidth) * boxesPerRow + "+")
    val middle = " " * offset + (("|" + " " * boxWidth) * boxesPerRow + "|")

    val startNum = (boxesPerRow * (rowIndex - 1)) + 1
    val numbers = (startNum until startNum + boxesPerRow).toList

    val numberLine = numbers.map { n =>
      val str = n.toString
      val padLeft = (boxWidth - str.length) / 2
      val padRight = boxWidth - str.length - padLeft
      "|" + " " * padLeft + str + " " * padRight
    }.mkString("")
    val numberLineWithOffset = " " * offset + numberLine + "|"

    val lines = (0 until boxHeight).map { i =>
      if (i == boxHeight / 2) numberLineWithOffset else middle
    }

    val bottom = if (includeBottom) " " * offset + PrintLine.printLine(totalWidth, boxesPerRow) else ""
    (Seq(horizontal) ++ lines ++ (if (bottom.nonEmpty) Seq(bottom) else Seq.empty)).mkString("\n")
  }
}
