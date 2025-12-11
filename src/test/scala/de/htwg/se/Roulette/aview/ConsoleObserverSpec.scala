package de.htwg.se.Roulette.aview

import de.htwg.se.Roulette.controller.{GameController, GameState}
import de.htwg.se.Roulette.model.RedBet
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import java.io.ByteArrayOutputStream

class ConsoleObserverSpec extends AnyWordSpec {
  "A ConsoleObserver" should {
    "print a message when a bet is placed" in {
      val gameController = new GameController()
      new ConsoleObserver(gameController)
      gameController.gameState = Some(GameState(1, List.empty)) // 1 is Red
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        gameController.placeBet(List(RedBet()))
      }
      out.toString.trim should be("ConsoleObserver: Bets placed: Red on result 1")
    }
  }
}
