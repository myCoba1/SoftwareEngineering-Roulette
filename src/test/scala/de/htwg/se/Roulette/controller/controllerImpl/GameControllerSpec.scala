package de.htwg.se.Roulette.controller.controllerImpl

import de.htwg.se.Roulette.model.bets.{BlackBet, RedBet}
import de.htwg.se.Roulette.model.fileIoComponent.fileIoXmlImpl.FileIO
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameControllerSpec extends AnyWordSpec with Matchers {
  "A GameController" should {
    // Assuming GameController takes FileIO as dependency or has a zero-arg constructor
    val fileIo = new FileIO()
    val controller = new GameController(fileIo)

    "have a valid initial state" in {
      controller.gameState shouldBe None
      controller.startRound()
      controller.gameState shouldBe defined
    }

    "place bets correctly" in {
      val bets = List(RedBet())
      controller.placeBet(bets)
      controller.gameState.get.bets should contain(RedBet())
    }

    "undo the last bet" in {
      controller.undo()
      controller.gameState.get.bets shouldBe empty
    }

    "handle a new round" in {
      val oldState = controller.gameState.get
      controller.startRound()
      val newState = controller.gameState.get
      
      // Ensure state has changed (e.g. winning number might be different, or bets cleared)
      // If bets are cleared on new round:
      newState.bets shouldBe empty
    }
  }
}