package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.Bet

sealed trait ControllerEvent
case class StateChanged(state: String) extends ControllerEvent
case class BetPlaced(bets: List[Bet], result: Int) extends ControllerEvent

class GameController extends Observable[ControllerEvent] {
  private var state: String = "idle"

  def getState: String = state

  def setState(newState: String): Unit = {
    state = newState
    notifyObservers(StateChanged(state))
  }

  def placeBet(bets: List[Bet], randomInt: Int): Unit = {
    // The logic to check if the bets are winners is in the bet objects themselves
    val results = bets.map(bet => (bet, bet.isWinningBet(randomInt)))

    // You can now update your model with the bet and the result
    // For example, you might have a player object that you update with winnings/losses

    notifyObservers(BetPlaced(bets, randomInt))
    setState(s"betsPlaced:${results.mkString(",")}") // optional state change notification
  }
}
