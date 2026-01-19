package de.htwg.se.Roulette.controller.controllerImpl

import de.htwg.se.Roulette.model.bets.Bet
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.util.Command

import scala.util.{Success, Try}

private[controllerImpl] class PlaceBetCommand(bets: List[Bet], controller: GameController)
    extends Command {
  private var previousState: Option[GameState] = None

  override def execute(): Try[Unit] = Try {
    previousState = controller.currentGameState
    controller.setBets(bets)
  }

  override def undo(): Try[Unit] =
    controller.restoreState(previousState)
}