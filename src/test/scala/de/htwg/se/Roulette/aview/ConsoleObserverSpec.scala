package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.GameController
import de.htwg.se.Roulette.model.{GameState, RedBet}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.ByteArrayOutputStream

class ConsoleObserverSpec extends AnyWordSpec with Matchers {
  "A ConsoleObserver" should {
    "print a message when a bet is placed" in {
      val gameController = new GameController()
      new ConsoleObserver(gameController)
      gameController.gameState = Some(GameState(1, List.empty)) // 1 is Red
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        gameController.placeBet(List(RedBet()))
      }
      val output = out.toString
      output should include ("Bets placed: Red")
      output should include ("You WON on your bet: Red")
    }
  }
}
