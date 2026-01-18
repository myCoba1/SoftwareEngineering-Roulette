package de.htwg.se.Roulette.controller.controllerImpl

import de.htwg.se.Roulette.controller.{BetPlaced, BetUndone, ControllerEvent, ControllerInterface, NewRound}
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.Bet
import de.htwg.se.Roulette.model.modelImpl.GameState

import scala.collection.mutable.ListBuffer
import scala.util.{Success, Try}

class GameController extends ControllerInterface {
  @volatile private[controllerImpl] var currentGameState: Option[GameState] = None
  override def gameState: Option[GameStateInterface] = currentGameState
  private val undoStack: ListBuffer[PlaceBetCommand] = ListBuffer.empty

  def startRound(): Unit = this.synchronized {
    val winningNumber = scala.util.Random.nextInt(37) // 0-36
    currentGameState = Some(GameState(winningNumber))
    currentGameState.foreach(gs => notifyObservers(NewRound(gs)))
  }

  private def executeCommand(command: PlaceBetCommand): Try[Unit] = this.synchronized {
    val result = command.execute()
    if (result.isSuccess) {
      undoStack.prepend(command)
    }
    result
  }

  def undo(): Try[Unit] = this.synchronized {
    if (undoStack.nonEmpty) {
      val command = undoStack.remove(0)
      command.undo()
    } else {
      Success(())
    }
  }

  override def placeBet(bets: List[Bet]): Unit = {
    executeCommand(new PlaceBetCommand(bets, this))
  }

  private[controllerImpl] def setBets(bets: List[Bet]): Unit = this.synchronized {
    currentGameState = currentGameState.map(_.copy(bets = bets))
    currentGameState.foreach(gs => notifyObservers(BetPlaced(gs)))
  }

  private[controllerImpl] def restoreState(state: Option[GameState]): Try[Unit] = Try { this.synchronized {
    currentGameState = state
    notifyObservers(BetUndone)
  }}
}
