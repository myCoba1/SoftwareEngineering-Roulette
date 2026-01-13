package src.main.scala


final class RouletteWorksheet$_ {
def args = RouletteWorksheet_sc.args$
def scriptPath = """src/main/scala/RouletteWorksheet.sc"""
/*<script>*/
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



/*</script>*/ /*<generated>*//*</generated>*/
}

object RouletteWorksheet_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new RouletteWorksheet$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export RouletteWorksheet_sc.script as `RouletteWorksheet`

