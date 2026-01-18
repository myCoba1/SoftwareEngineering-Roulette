package de.htwg.se.Roulette.controller.controllerImpl

import de.htwg.se.Roulette.model.bets.RedBet
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlaceBetCommandSpec extends AnyWordSpec with Matchers {
  "A PlaceBetCommand" should {
    "execute and undo a bet placement" in {
      val controller = new GameController()
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