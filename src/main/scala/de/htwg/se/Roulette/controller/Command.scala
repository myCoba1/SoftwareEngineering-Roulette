package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.Bet
import scala.util.{Success, Try}

trait Command {
  def execute(): Try[Unit]
  def undo(): Try[Unit]
}

class PlaceBetCommand(bets: List[Bet], controller: GameController)
    extends Command {
  private var previousState: Option[GameState] = None

  override def execute(): Try[Unit] = Try {
    previousState = controller.gameState
    controller.placeBet(bets)
  }

  override def undo(): Try[Unit] =
    previousState.map(controller.restoreState).getOrElse(Success(()))
}