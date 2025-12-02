package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.GameController
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class RouletteRoundSpec extends AnyWordSpec {
  "The RouletteRound object" should {
    "run a round and continue" in {
      val gameController = new GameController()
      val in = new ByteArrayInputStream("R\ny\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          val continue = RouletteRound.rouletteRound(gameController)
          continue should be(true)
        }
      }
      out.toString should include("Play another round? (y/n):")
    }
    "run a round and not continue" in {
      val gameController = new GameController()
      val in = new ByteArrayInputStream("R\nn\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          val continue = RouletteRound.rouletteRound(gameController)
          continue should be(false)
        }
      }
      out.toString should include("Play another round? (y/n):")
    }
  }
}
