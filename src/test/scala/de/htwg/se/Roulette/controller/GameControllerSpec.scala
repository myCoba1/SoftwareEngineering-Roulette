package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.{Bet, BlackBet, RedBet}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

class GameControllerSpec extends AnyWordSpec with Matchers {
  "A GameController" should {
    "start with no gameState" in {
      val controller = new GameController()
      controller.gameState should be(None)
    }

    "start a new round" in {
      val controller = new GameController()
      var notified = false
      val observer = new Observer[ControllerEvent] {
        override def update(event: ControllerEvent): Unit = event match {
          case NewRound(gs) =>
            gs.winningNumber should be >= 0
            gs.winningNumber should be <= 36
            gs.bets should be(empty)
            notified = true
          case _ =>
        }
      }
      controller.addObserver(observer)
      controller.startRound()
      controller.gameState should be(defined)
      notified should be(true)
    }

    "place a bet" in {
      val controller = new GameController()
      controller.startRound()
      var notified = false
      val bet = RedBet()
      val observer = new Observer[ControllerEvent] {
        override def update(event: ControllerEvent): Unit = event match {
          case BetPlaced(gs) =>
            gs.bets should contain(bet)
            notified = true
          case _ =>
        }
      }
      controller.addObserver(observer)
      controller.placeBet(List(bet))
      notified should be(true)
    }

    "execute and undo a command" in {
      val controller = new GameController()
      controller.startRound()
      val initialBets = controller.gameState.get.bets
      val command = new PlaceBetCommand(List(BlackBet()), controller)
      controller.executeCommand(command) should be(a[Success[_]])
      controller.gameState.get.bets should contain(BlackBet())
      controller.undo() should be(a[Success[_]])
      controller.gameState.get.bets should be(initialBets)
    }
  }
}
