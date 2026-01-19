package de.htwg.se.Roulette.controller.controllerImpl

import de.htwg.se.Roulette.model.bets.RedBet
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlaceBetCommandSpec extends AnyWordSpec with Matchers {
  val mockFileIO = new FileIOInterface {
    override def load: GameStateInterface = GameState(0, List.empty)
    override def save(gameState: GameStateInterface): Unit = {}
  }
  "A PlaceBetCommand" should {
    "execute and undo a bet placement" in {
      val controller = new GameController(mockFileIO)
      controller.startRound()
      
      // Capture state before command
      val previousState = controller.currentGameState
      
      val bets = List(RedBet())
      val command = new PlaceBetCommand(bets, controller)
      
      // Execute
      command.execute()
      controller.currentGameState.get.bets should be(bets)
      
      // Undo
      command.undo()
      controller.currentGameState should be(previousState)
    }
  }
}