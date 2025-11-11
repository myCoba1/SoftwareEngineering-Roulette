object Roulette {
  def main(args: Array[String]): Unit = {
    print(printTable(11, 1))  // (11*n+(n-1),n)
  }

  private def computeHexOffset(totalWidth: Int, height: Int): Int = {
    // offset = round(totalWidth * height / (totalWidth + height)), at least 2
    math.max(2, math.round(totalWidth.toDouble * height / (totalWidth + height)).toInt)
  }

  private def printTable(totalWidth: Int, height: Int): String = {
    val bigBoxHeight = height
    val smallBoxHeight = height

    val hexOffset = computeHexOffset(totalWidth, height)
    val top = " " * hexOffset + printLine(totalWidth, 3)+ "\n"
    val hexagon = printHexagon(totalWidth, height)

    val firstRow = printBoxRow(totalWidth, bigBoxHeight, 1, includeBottom = false, rowIndex = 0, offset = hexOffset)
    val middleRows = (1 until 13).map(i =>
      printBoxRow(totalWidth, smallBoxHeight, 3, includeBottom = false, rowIndex = i, offset = hexOffset)
    )
    val bottom = " " * hexOffset + printLine(totalWidth, 3)

    (Seq(top+hexagon, firstRow) ++ middleRows :+ bottom).mkString("\n")
  }

  private def printHexagon(totalWidth: Int, height: Int): String = {
    val hexHeight = height
    val hexInnerWidth = totalWidth + 2  // width of flat part inside hex
    val maxInner = hexInnerWidth + 2 * hexHeight

    // Top slanted edges
    val top = (0 until hexHeight).map { i =>
      val spaces = " " * (hexHeight - i)
      val inner = " " * (hexInnerWidth + 2*i)  // expand width as slants move out
      spaces + "/" + inner + "\\"
    }

    // Middle flat section
    val randomInt = scala.util.Random.nextInt(36)
    val randStrRaw = randomInt.toString
    val randStr = if (randStrRaw.length > maxInner) randStrRaw.take(maxInner) else randStrRaw

    val middleHeight = math.max(hexHeight / 2, 1)
    val midIndex = middleHeight / 2
    val middle = (0 until middleHeight).map { i =>
      val innerSpaces = " " * maxInner
      if (i == midIndex) {
        val padLeft = (maxInner - randStr.length) / 2
        val padRight = maxInner - randStr.length - padLeft
         "|" + " " * padLeft + randStr + " " * padRight + "|"
      }else {
        "|" + innerSpaces + "|"
      }
    }

    // Bottom slanted edges
    val bottom = (0 until hexHeight).reverse.map { i =>
      val spaces = " " * (hexHeight - i)
      val inner = " " * (hexInnerWidth + 2*i)  // expand width as slants move out
      spaces + "\\" + inner + "/"
    }

    (top ++ middle ++ bottom).mkString("\n")
  }

  private def printBoxRow(totalWidth: Int, boxHeight: Int, boxesPerRow: Int, includeBottom: Boolean, rowIndex: Int, offset: Int = 0): String = {
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

    val bottom = if (includeBottom) " " * offset + printLine(totalWidth, boxesPerRow) else ""
    (Seq(horizontal) ++ lines ++ (if (bottom.nonEmpty) Seq(bottom) else Seq.empty)).mkString("\n")
  }

  private def printLine(totalWidth: Int, boxesPerRow: Int): String = {
    val boxWidth = totalWidth / boxesPerRow
    ("+" + "-" * boxWidth) * boxesPerRow + "+"
  }
}