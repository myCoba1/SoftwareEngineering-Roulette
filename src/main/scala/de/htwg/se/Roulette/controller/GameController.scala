package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.Bet
import scala.collection.mutable.ListBuffer
import scala.util.{Success, Try}

sealed trait ControllerEvent
case class NewRound(gameState: GameState) extends ControllerEvent
case class BetPlaced(gameState: GameState) extends ControllerEvent
case object BetUndone extends ControllerEvent

class GameController extends Observable[ControllerEvent] {
  var gameState: Option[GameState] = None
  private val undoStack: ListBuffer[Command] = ListBuffer.empty

  def startRound(): Unit = {
    val winningNumber = scala.util.Random.nextInt(37) // 0-36
    gameState = Some(GameState(winningNumber))
    gameState.foreach(gs => notifyObservers(NewRound(gs)))
  }

  def executeCommand(command: Command): Try[Unit] = {
    val result = command.execute()
    if (result.isSuccess) {
      undoStack.prepend(command)
    }
    result
  }

  def undo(): Try[Unit] = {
    undoStack.headOption
      .map(cmd => {
        val result = cmd.undo()
        if (result.isSuccess) {
          undoStack.remove(0)
        }
        result
      })
      .getOrElse(Success(()))
  }

  def placeBet(bets: List[Bet]): Unit = {
    gameState = gameState.map(_.copy(bets = bets))
    gameState.foreach(gs => notifyObservers(BetPlaced(gs)))
  }

  def restoreState(state: GameState): Try[Unit] = Try {
    gameState = Some(state)
    notifyObservers(BetUndone)
  }
}
