package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.controller.{ControllerEvent, ControllerInterface}
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.{Bet, RedBet}
import de.htwg.se.Roulette.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayOutputStream
import scala.util.{Success, Try}

class TuiSpec extends AnyWordSpec with Matchers {

  class MockController extends ControllerInterface {
    var startRoundCalled = false
    var undoCalled = false
    var saveCalled = false
    var loadCalled = false
    var placedBets: List[Bet] = List.empty

    override def startRound(): Unit = startRoundCalled = true
    override def undo(): Try[Unit] = { undoCalled = true; Success(()) }
    override def placeBet(bets: List[Bet]): Unit = placedBets = bets
    override def gameState: Option[GameStateInterface] = None
    override def save(): Unit = saveCalled = true
    override def load(): Unit = loadCalled = true
    
    override def addObserver(o: Observer[ControllerEvent]): Unit = {}
    override def removeObserver(o: Observer[ControllerEvent]): Unit = {}
  }

  "A Tui" should {
    val controller = new MockController
    val tui = new Tui(controller)

    "start a new round on input 'new'" in {
      tui.processInput("new")
      controller.startRoundCalled should be(true)
    }

    "undo the last step on input 'undo'" in {
      tui.processInput("undo")
      controller.undoCalled should be(true)
    }

    "save the game on input 'save'" in {
      tui.processInput("save")
      controller.saveCalled should be(true)
    }

    "load the game on input 'load'" in {
      tui.processInput("load")
      controller.loadCalled should be(true)
    }

    "set stake on input 'stake <amount>'" in {
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        tui.processInput("stake 50")
      }
      tui.currentStake should be(50)
      out.toString should include("Stake set to 50")
    }

    "handle invalid stake amount" in {
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        tui.processInput("stake -10")
        tui.processInput("stake abc")
      }
      out.toString should include("Invalid stake amount")
    }

    "place a bet on valid bet input" in {
      tui.currentStake = 10
      tui.processInput("red")
      controller.placedBets should contain(RedBet(10))
    }

    "print an error message on invalid input" in {
      val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        tui.processInput("invalid input")
      }
      out.toString should include("Invalid command or bet.")
    }
    
    "print an error message on empty bet result" in {
       val out = new ByteArrayOutputStream()
      Console.withOut(out) {
        // "xyz" returns empty list of bets
        tui.processInput("xyz")
      }
      out.toString should include("Invalid command or bet.")
    }
  }
}