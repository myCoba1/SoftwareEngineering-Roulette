package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.aview.aviewImpl.ConsoleObserver
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.model.bets.LineOneBet
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.ByteArrayOutputStream

class ConsoleObserverSpec extends AnyWordSpec with Matchers {
  "A ConsoleObserver" should {
    "print a message when a bet is placed" in {
      val gameController = new GameController()
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
      val controller = new GameController()
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
      val controller = new GameController()
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
