package de.htwg.se.Roulette.controller.controllerImpl

import com.google.inject.Inject
import de.htwg.se.Roulette.controller.{BetPlaced, BetUndone, ControllerEvent, ControllerInterface, NewRound}
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.Bet
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.modelImpl.GameState

import scala.collection.mutable.ListBuffer
import scala.util.{Success, Try}

class GameController @Inject() (fileIo: FileIOInterface) extends ControllerInterface {
  @volatile private[controllerImpl] var currentGameState: Option[GameState] = None
  override def gameState: Option[GameStateInterface] = currentGameState
  private val undoStack: ListBuffer[PlaceBetCommand] = ListBuffer.empty

  def startRound(): Unit = this.synchronized {
    val currentBalance = currentGameState.map(_.balance).getOrElse(100)
    if (currentBalance <= 0) {
      // Game Over: Reset or just notify? For now, we reset if they try to start a new round with 0.
      // Or we could block it. Let's assume a reset for a new game.
    }
    val winningNumber = scala.util.Random.nextInt(37) // 0-36
    currentGameState = Some(GameState(winningNumber, List.empty, if (currentBalance <= 0) 100 else currentBalance))
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
    currentGameState match {
      case Some(gs) =>
        val totalAmount = bets.map(_.amount).sum
        val newBalance = gs.balance - totalAmount + bets.map(_.payout(gs.winningNumber)).sum
        val newGs = gs.copy(bets = bets, balance = newBalance)
        currentGameState = Some(newGs)
        notifyObservers(BetPlaced(newGs))
      case None =>
    }
  }

  private[controllerImpl] def restoreState(state: Option[GameState]): Try[Unit] = Try { this.synchronized {
    currentGameState = state
    notifyObservers(BetUndone)
  }}

  override def save(): Unit = {
    currentGameState.foreach(fileIo.save)
  }

  override def load(): Unit = {
    val loadedState = fileIo.load
    currentGameState = Some(loadedState.asInstanceOf[GameState])
    notifyObservers(NewRound(loadedState))
  }
}
