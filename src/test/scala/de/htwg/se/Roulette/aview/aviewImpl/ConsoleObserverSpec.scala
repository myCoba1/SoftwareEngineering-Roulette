package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.controller.{BetPlaced, BetUndone, ControllerEvent, ControllerInterface, NewRound}
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.model.bets.{Bet, RedBet}
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.io.ByteArrayOutputStream
import scala.util.{Success, Try}

class ConsoleObserverSpec extends AnyWordSpec with Matchers {

  class MockController extends ControllerInterface {
    override def addObserver(o: Observer[ControllerEvent]): Unit = {}
    override def removeObserver(o: Observer[ControllerEvent]): Unit = {}
    override def placeBet(bets: List[Bet]): Unit = {}
    override def startRound(): Unit = {}
    override def undo(): Try[Unit] = Success(())
    override def save(): Unit = {}
    override def load(): Unit = {}
    override def gameState: Option[GameStateInterface] = Some(GameState(0, List()))
  }

  "A ConsoleObserver" should {
    val controller = new MockController
    val console = new ConsoleObserver(controller)

    "print correct output on NewRound event" in {
      val stream = new ByteArrayOutputStream()
      Console.withOut(stream) {
        val mockGameState = GameState(0, List(), 100)
        console.update(NewRound(mockGameState))
      }
      val output = stream.toString
      output should include("New round started.")
      output should include("Current Balance: 100")
    }

    "print correct output on BetPlaced event" in {
      val stream = new ByteArrayOutputStream()
      Console.withOut(stream) {
        val mockGameState = GameState(winningNumber = 5, bets = List(RedBet(10)), balance = 90)
        console.update(BetPlaced(mockGameState))
      }
      val output = stream.toString
      output should include("Bets placed: Red (10)")
      output should include("New Balance: 90")
    }

    "print correct output on BetUndone event" in {
      val stream = new ByteArrayOutputStream()
      Console.withOut(stream) {
        console.update(BetUndone)
      }
      stream.toString should include("Last bet was undone.")
    }
  }
}