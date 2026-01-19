package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.aview.aviewImpl.PlaceBet
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.RedBet
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class PlaceBetSpec extends AnyWordSpec with Matchers {
  val mockFileIO = new FileIOInterface {
    override def load: GameStateInterface = GameState(0, List.empty)
    override def save(gameState: GameStateInterface): Unit = {}
  }

  "The PlaceBet object" should {
    "place a bet by executing a command" in {
      val gameController = new GameController(mockFileIO)
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
      val gameController = new GameController(mockFileIO)
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
