package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.aview.aviewImpl.PlaceBet
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.RedBet
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class PlaceBetSpec extends AnyWordSpec with Matchers {
  "The PlaceBet object" should {
    "place a bet by executing a command" in {
      val gameController = new GameController()
      gameController.startRound()
      val in = new ByteArrayInputStream("R\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          PlaceBet.placeBet(gameController)
        }
      }
      gameController.gameState.get.bets should contain(RedBet())
      out.toString should include("Place your Bet(s) (e.g., R 1/3 22): ")
    }

    "handle invalid input" in {
      val gameController = new GameController()
      gameController.startRound()
      val in = new ByteArrayInputStream("invalid\nR\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          PlaceBet.placeBet(gameController)
        }
      }
      out.toString should include("Invalid input.")
      gameController.gameState.get.bets should contain(RedBet())
    }
  }
}
