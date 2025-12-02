package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.{BetPlaced, GameController}
import de.htwg.se.Roulette.model.BetFactory
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import java.io.ByteArrayOutputStream

class ConsoleObserverSpec extends AnyWordSpec {
  "A ConsoleObserver" should {
    "print a message when a bet is placed" in {
      val gameController = new GameController()
      val observer = new ConsoleObserver(gameController)
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        gameController.placeBet(BetFactory.getBets("Red"), 1)
      }
      out.toString should include("ConsoleObserver: Bets placed: Red on result 1")
    }
  }
}
