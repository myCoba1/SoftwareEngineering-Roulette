package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.controller.{BetPlaced, BetUndone, ControllerEvent, ControllerInterface, NewRound}
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.model.bets.Bet
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

    "update correctly on events" in {
      val stream = new ByteArrayOutputStream()
      Console.withOut(stream) {
        val mockGameState = GameState(0, List())
        
        noException should be thrownBy console.update(NewRound(mockGameState))
        noException should be thrownBy console.update(BetPlaced(mockGameState))
        noException should be thrownBy console.update(BetUndone)
      }
      // If specific output is expected, we could assert on stream.toString
    }
  }
}