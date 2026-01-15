package de.htwg.se.Roulette.model

trait BetParser {
  private var next: Option[BetParser] = None

  def setNext(parser: BetParser): BetParser = {
    next = Some(parser)
    parser
  }


  def parse(betString: String): Option[Bet] = {
    handle(betString) match {
      case someBet @ Some(_) => someBet // If handler can parse it return the result
      case None => next.flatMap(_.parse(betString)) // pass to the next handler
    }
  }

  protected def handle(betString: String): Option[Bet]
}

class NumberBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    betString.toIntOption.filter(n => n >= 0 && n <= 36).map(NumberBet.apply)
}

class RedBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("red", "r").contains(betString.trim.toLowerCase)) Some(RedBet()) else None
}

class BlackBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("black", "b").contains(betString.trim.toLowerCase)) Some(BlackBet()) else None
}

class FirstThirdBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("1/3", "1t").contains(betString.trim.toLowerCase)) Some(FirstThirdBet()) else None
}

class SecondThirdBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("2/3", "2t").contains(betString.trim.toLowerCase)) Some(SecondThirdBet()) else None
}

class ThirdThirdBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("3/3", "3t").contains(betString.trim.toLowerCase)) Some(ThirdThirdBet()) else None
}

class FirstHalfBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("1/2", "1h").contains(betString.trim.toLowerCase)) Some(FirstHalfBet()) else None
}

class SecondHalfBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("2/2", "2h").contains(betString.trim.toLowerCase)) Some(SecondHalfBet()) else None
}

class EvenBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("even", "e").contains(betString.trim.toLowerCase)) Some(EvenBet()) else None
}

class OddBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] =
    if (Set("odd", "o").contains(betString.trim.toLowerCase)) Some(OddBet()) else None
}

class LineOneBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] = 
    if (Set("12-1", "1l").contains(betString.trim.toLowerCase)) Some(LineOneBet()) else None
}

class LineTwoBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] = 
    if (Set("22-1", "2l").contains(betString.trim.toLowerCase)) Some(LineTwoBet()) else None
}

class LineThreeBetParser extends BetParser {
  override protected def handle(betString: String): Option[Bet] = 
    if (Set("32-1", "3l").contains(betString.trim.toLowerCase)) Some(LineThreeBet()) else None
}