package de.htwg.se.Roulette.model

object Thirds {
  val firstThird: Set[Int] = (1 to 12).toSet
  val secondThird: Set[Int] = (13 to 24).toSet
  val thirdThird: Set[Int] = (25 to 36).toSet

  def thirdOf(n: Int): String = n match {
    case x if firstThird.contains(x) => "1/3"
    case x if secondThird.contains(x) => "2/3"
    case x if thirdThird.contains(x) => "3/3"
    case _ => "None"
  }
}

case class FirstThirdBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    Thirds.firstThird.contains(winningNumber)
  }
  override def toString: String = "1 st 12"
}

case class SecondThirdBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    Thirds.secondThird.contains(winningNumber)
  }
  override def toString: String = "2 nd 12"
}

case class ThirdThirdBet() extends Bet {
  override def isWinningBet(winningNumber: Int): Boolean = {
    Thirds.thirdThird.contains(winningNumber)
  }
  override def toString: String = "3 rd 12"
}
