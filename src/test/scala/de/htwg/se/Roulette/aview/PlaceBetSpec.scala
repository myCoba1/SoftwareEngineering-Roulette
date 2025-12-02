package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.GameController
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class PlaceBetSpec extends AnyWordSpec {
  "The PlaceBet object" should {
    "place a bet and print a winning message" in {
      val gameController = new GameController()
      val in = new ByteArrayInputStream("R\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          PlaceBet.placeBet(gameController, 1) // 1 is Red
        }
      }
      out.toString should include("You won on your bet: Red")
    }
    "place a bet and print a losing message" in {
      val gameController = new GameController()
      val in = new ByteArrayInputStream("B\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          PlaceBet.placeBet(gameController, 1) // 1 is Red
        }
      }
      out.toString should include("You lost on your bet: Black")
    }
    "handle invalid input" in {
      val gameController = new GameController()
      val in = new ByteArrayInputStream("invalid\nR\n".getBytes)
      val out = new ByteArrayOutputStream()
      Console.withIn(in) {
        Console.withOut(out) {
          PlaceBet.placeBet(gameController, 1) // 1 is Red
        }
      }
      out.toString should include("Invalid input.")
      out.toString should include("You won on your bet: Red")
    }
  }
}
