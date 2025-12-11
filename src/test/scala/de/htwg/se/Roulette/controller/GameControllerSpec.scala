package de.htwg.se.Roulette.controller

import de.htwg.se.Roulette.model.{Bet, RedBet}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameControllerSpec extends AnyWordSpec with Matchers {
  "A GameController" should {
    "have an initial state of 'idle'" in {
      val controller = new GameController()
      controller.getState should be("idle")
    }

    "be able to set a new state" in {
      val controller = new GameController()
      controller.setState("newState")
      controller.getState should be("newState")
    }

    "notify observers when the state changes" in {
      val controller = new GameController()
      var notified = false
      val observer = new Observer[ControllerEvent] {
        override def update(event: ControllerEvent): Unit = {
          event match {
            case StateChanged("newState") => notified = true
            case _ =>
          }
        }
      }
      controller.addObserver(observer)
      controller.setState("newState")
      notified should be(true)
    }

    "notify observers when a bet is placed" in {
      val controller = new GameController()
      var notified = false
      val bet = RedBet()
      val observer = new Observer[ControllerEvent] {
        override def update(event: ControllerEvent): Unit = {
          event match {
            case BetPlaced(bets, result) =>
              bets should contain(bet)
              result should be(1)
              notified = true
            case _ =>
          }
        }
      }
      controller.addObserver(observer)
      controller.placeBet(List(bet), 1)
      notified should be(true)
    }
  }
}
