def printBottomLine(totalWidth: Int, boxesPerRow: Int): String = {
  val boxWidth = totalWidth / boxesPerRow
  val horizontal = ("+" + "-" * boxWidth) * boxesPerRow + "+"
  horizontal
}
printBottomLine(totalWidth = 5, boxesPerRow = 3)
printBottomLine(totalWidth = 10, boxesPerRow = 5)
printBottomLine(totalWidth = 20, boxesPerRow = 3)
printBottomLine(totalWidth = 13, boxesPerRow = 3)


