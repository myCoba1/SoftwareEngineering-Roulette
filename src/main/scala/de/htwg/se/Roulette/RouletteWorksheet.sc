private def printHexagon(width: Int): String = {
  val sb = new StringBuilder
  val height = width / 2  // approximate height for a hexagon
  // Top
  for (i <- 0 until height) {
    val spaces = " " * (height - i)
    val middle = "-" * (width + i * 2)
    sb.append(spaces + "/" + middle + "\\\n")
  }
  // Bottom
  for (i <- (0 until height).reverse) {
    val spaces = " " * (height - i)
    val middle = "-" * (width + i * 2)
    sb.append(spaces + "\\" + middle + "/\n")
  }


