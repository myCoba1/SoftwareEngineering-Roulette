package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.aview.aviewImpl.ConsoleObserver
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.LineOneBet
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayOutputStream

class ConsoleObserverSpec extends AnyWordSpec with Matchers {
  val mockFileIO = new FileIOInterface {
    override def load: GameStateInterface = GameState(0, List.empty)
    override def save(gameState: GameStateInterface): Unit = {}
  }
  "A ConsoleObserver" should {
    "print a message when a bet is placed" in {
      val gameController = new GameController(mockFileIO)
      gameController.startRound() // A round must be started for a bet to be placed.
      new ConsoleObserver(gameController)
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        gameController.placeBet(List(LineOneBet()))
      }
      val output = out.toString
      output should include("Bets placed: 1. 2-1")

      // Since the winning number is random, the test should account for both win and loss scenarios.
      val hasWon = output.contains("You WON on your bet: 1. 2-1")
      val hasLost = output.contains("You LOST on your bet: 1. 2-1")
      (hasWon || hasLost) should be(true)
    }

    "print a message on a new round" in {
      val controller = new GameController(mockFileIO)
      new ConsoleObserver(controller)
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        controller.startRound()
      }
      val output = out.toString
      output should include("New round started.")
      output should include("Place your bet(s)")
    }

    "print a message when a bet is undone" in {
      val controller = new GameController(mockFileIO)
      controller.startRound()
      new ConsoleObserver(controller)
      controller.placeBet(List(LineOneBet())) // Place a bet to be undone
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        controller.undo()
      }
      val output = out.toString
      output should include("Last bet was undone.")
      output should include("You can place a new bet.")
    }
  }
}
