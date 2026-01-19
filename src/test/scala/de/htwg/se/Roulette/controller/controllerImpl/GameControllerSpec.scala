package de.htwg.se.Roulette.controller.controllerImpl

import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.controller.{BetPlaced, ControllerEvent, NewRound}
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.{BlackBet, RedBet}
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success

class GameControllerSpec extends AnyWordSpec with Matchers {
  val mockFileIO = new FileIOInterface {
    override def load: GameStateInterface = GameState(0, List.empty)
    override def save(gameState: GameStateInterface): Unit = {}
  }
  "A GameController" should {
    "start with no gameState" in {
      val controller = new GameController(mockFileIO)
      controller.gameState should be(None)
    }

    "start a new round" in {
      val controller = new GameController(mockFileIO)
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
      val controller = new GameController(mockFileIO)
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

    "undo a placed bet" in {
      val controller = new GameController(mockFileIO)
      controller.startRound()
      val initialBets = controller.gameState.get.bets
      controller.placeBet(List(BlackBet()))
      controller.gameState.get.bets should contain(BlackBet())
      controller.undo() should be(a[Success[_]])
      controller.gameState.get.bets should be(initialBets)
    }
  }
}
