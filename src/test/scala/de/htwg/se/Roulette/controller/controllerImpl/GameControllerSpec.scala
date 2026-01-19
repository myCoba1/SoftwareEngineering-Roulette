package de.htwg.se.Roulette.controller.controllerImpl

import de.htwg.se.Roulette.controller.{BetPlaced, BetUndone, ControllerEvent, NewRound}
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.RedBet
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameControllerSpec extends AnyWordSpec with Matchers {

  class MockFileIO extends FileIOInterface {
    var saveCalled = false
    var loadCalled = false
    override def load: GameStateInterface = {
      loadCalled = true
      GameState(0, List(), 1000)
    }
    override def save(gameState: GameStateInterface): Unit = saveCalled = true
  }

  "A GameController" should {
    val fileIo = new MockFileIO
    val controller = new GameController(fileIo)
    
    var events = List.empty[ControllerEvent]
    val observer = new Observer[ControllerEvent] {
      override def update(event: ControllerEvent): Unit = events = event :: events
    }
    controller.addObserver(observer)

    "have no game state initially" in {
      controller.gameState shouldBe None
    }

    "start a round correctly" in {
      controller.startRound()
      controller.gameState shouldBe defined
      events.head shouldBe a [NewRound]
      controller.gameState.get.balance shouldBe 100
      
      // Force winning number to 2 (Black) to ensure deterministic behavior for betting tests
      val currentState = controller.currentGameState.get
      controller.currentGameState = Some(currentState.copy(winningNumber = 2))
    }

    "place bets correctly" in {
      val bets = List(RedBet(10))
      controller.placeBet(bets)
      controller.gameState.get.bets should contain(RedBet(10))
      controller.gameState.get.balance shouldBe 90 // 100 - 10
      events.head shouldBe a [BetPlaced]
    }

    "undo the last bet" in {
      controller.undo()
      controller.gameState.get.bets shouldBe empty
      controller.gameState.get.balance shouldBe 100
      events.head shouldBe BetUndone
    }
    
    "handle undo with empty stack" in {
      controller.undo() // Already empty
      // Should not throw and state remains same
      controller.gameState.get.bets shouldBe empty
    }

    "reset balance if it drops to zero or below on new round" in {
      // Force balance to 0
      val bets = List(RedBet(100))
      controller.placeBet(bets)
      controller.gameState.get.balance shouldBe 0
      
      // Start new round
      controller.startRound()
      controller.gameState.get.balance shouldBe 100
    }

    "save the game" in {
      controller.save()
      fileIo.saveCalled shouldBe true
    }

    "load the game" in {
      controller.load()
      fileIo.loadCalled shouldBe true
      controller.gameState.get.balance shouldBe 1000
      events.head shouldBe a [NewRound]
    }
  }
}