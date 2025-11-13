package de.htwg.se.Roulette.model

object PrintHex {
  def printHex(totalWidth: Int, height: Int, randomInt: Option[Int]): String = {
    val hexHeight = height
    val hexInnerWidth = totalWidth + 2  // width of flat part inside hex
    val maxInner = hexInnerWidth + 2 * hexHeight

    // Top
    val top = (0 until hexHeight).map { i =>
      val spaces = " " * (hexHeight - i)
      val inner = " " * (hexInnerWidth + 2*i)  // ++width as slants move out
      spaces + "/" + inner + "\\"
    }

    // Middle with RandNum
    val randStrRaw = randomInt.map(_.toString).getOrElse(" ")
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

    // Bottom
    val bottom = (0 until hexHeight).reverse.map { i =>
      val spaces = " " * (hexHeight - i)
      val inner = " " * (hexInnerWidth + 2*i)  // expand width as slants move out
      spaces + "\\" + inner + "/"
    }

    (top ++ middle ++ bottom).mkString("\n")
  }
}
