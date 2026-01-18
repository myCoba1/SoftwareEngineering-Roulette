package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.aview.aviewImpl.{Continue, Quit, RouletteRound, Undo}
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class RouletteRoundSpec extends AnyWordSpec with Matchers {
  "The RouletteRound object" should {
    "run a round and return Continue" in {
      val gameController = new GameController()
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
      val gameController = new GameController()
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
      val gameController = new GameController()
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
