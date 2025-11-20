package de.htwg.se.Roulette.controller

sealed trait ControllerEvent
case class StateChanged(state: String) extends ControllerEvent
case class BetPlaced(bet: Any, result: Int) extends ControllerEvent

class GameController extends Observable[ControllerEvent] {
  private var state: String = "idle"

  def getState: String = state

  def setState(newState: String): Unit = {
    state = newState
    notifyObservers(StateChanged(state))
  }

  def placeBet(bet: Any, randomInt: Int): Unit = {
    // update model logic here...
    notifyObservers(BetPlaced(bet, randomInt))
    setState(s"betPlaced:$bet:$randomInt") // optional state change notification
  }
}
