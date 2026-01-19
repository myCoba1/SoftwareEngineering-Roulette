package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.aview.aviewImpl.{Continue, Quit, RouletteRound, Undo}
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class RouletteRoundSpec extends AnyWordSpec with Matchers {
  val mockFileIO = new FileIOInterface {
    override def load: GameStateInterface = GameState(0, List.empty)
    override def save(gameState: GameStateInterface): Unit = {}
  }
  "The RouletteRound object" should {
    "run a round and return Continue" in {
      val gameController = new GameController(mockFileIO)
      gameController.startRound()
      val in = new ByteArrayInputStream("R\ny\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          val action = RouletteRound.rouletteRound(gameController)
          action should be(Continue)
        }
      }
      out.toString should include("Play another round? (y/n/undo):")
    }
    "run a round and return Quit" in {
      val gameController = new GameController(mockFileIO)
      gameController.startRound()
      val in = new ByteArrayInputStream("R\nn\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          val action = RouletteRound.rouletteRound(gameController)
          action should be(Quit)
        }
      }
      out.toString should include("Play another round? (y/n/undo):")
    }
    "run a round and return Undo" in {
      val gameController = new GameController(mockFileIO)
      gameController.startRound()
      val in = new ByteArrayInputStream("R\nundo\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          val action = RouletteRound.rouletteRound(gameController)
          action should be(Undo)
        }
      }
      out.toString should include("Play another round? (y/n/undo):")
    }
  }
}
