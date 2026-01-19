package de.htwg.se.Roulette.model.bets

import de.htwg.se.Roulette.model.bets.{Bet, NumberBet}

trait BetParser {
  private var next: Option[BetParser] = None

  def setNext(parser: BetParser): BetParser = {
    next = Some(parser)
    parser
  }


  def parse(betString: String, amount: Int): Option[Bet] = {
    handle(betString, amount) match {
      case someBet @ Some(_) => someBet // If handler can parse it return the result
      case None => next.flatMap(_.parse(betString, amount)) // pass to the next handler
    }
  }

  protected def handle(betString: String, amount: Int): Option[Bet]
}

class NumberBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    betString.toIntOption.filter(n => n >= 0 && n <= 36).map(n => NumberBet(n, amount))
}

class RedBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("red", "r").contains(betString.trim.toLowerCase)) Some(RedBet(amount)) else None
}

class BlackBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("black", "b").contains(betString.trim.toLowerCase)) Some(BlackBet(amount)) else None
}

class FirstThirdBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("1/3", "1t").contains(betString.trim.toLowerCase)) Some(FirstThirdBet(amount)) else None
}

class SecondThirdBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("2/3", "2t").contains(betString.trim.toLowerCase)) Some(SecondThirdBet(amount)) else None
}

class ThirdThirdBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("3/3", "3t").contains(betString.trim.toLowerCase)) Some(ThirdThirdBet(amount)) else None
}

class FirstHalfBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("1/2", "1h").contains(betString.trim.toLowerCase)) Some(FirstHalfBet(amount)) else None
}

class SecondHalfBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("2/2", "2h").contains(betString.trim.toLowerCase)) Some(SecondHalfBet(amount)) else None
}

class EvenBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("even", "e").contains(betString.trim.toLowerCase)) Some(EvenBet(amount)) else None
}

class OddBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] =
    if (Set("odd", "o").contains(betString.trim.toLowerCase)) Some(OddBet(amount)) else None
}

class LineOneBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] = 
    if (Set("12-1", "1l").contains(betString.trim.toLowerCase)) Some(LineOneBet(amount)) else None
}

class LineTwoBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] = 
    if (Set("22-1", "2l").contains(betString.trim.toLowerCase)) Some(LineTwoBet(amount)) else None
}

class LineThreeBetParser extends BetParser {
  override protected def handle(betString: String, amount: Int): Option[Bet] = 
    if (Set("32-1", "3l").contains(betString.trim.toLowerCase)) Some(LineThreeBet(amount)) else None
}