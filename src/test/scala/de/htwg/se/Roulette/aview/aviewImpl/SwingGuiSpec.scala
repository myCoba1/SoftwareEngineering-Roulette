package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.controller.{BetPlaced, BetUndone, ControllerEvent, ControllerInterface, NewRound}
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets.{Bet, RedBet}
import de.htwg.se.Roulette.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.util.{Success, Try}
import java.awt.GraphicsEnvironment

class SwingGuiSpec extends AnyWordSpec with Matchers {

  class MockController extends ControllerInterface {
    var betsPlaced: List[Bet] = List()
    var roundStarted = false
    var undoCalled = false

    override def addObserver(o: Observer[ControllerEvent]): Unit = {}
    override def removeObserver(o: Observer[ControllerEvent]): Unit = {}
    override def placeBet(bets: List[Bet]): Unit = { betsPlaced = bets }
    override def startRound(): Unit = { roundStarted = true }
    override def undo(): Try[Unit] = { undoCalled = true; Success(()) }
    override def save(): Unit = {}
    override def load(): Unit = {}
    override def gameState: Option[GameStateInterface] = None
  }

  "A SwingGui" should {
    if (GraphicsEnvironment.isHeadless) {
      "be skipped in headless environment" in {
        cancel("Running in headless environment")
      }
    } else {
      val controller = new MockController
      val gui = new SwingGui(controller)

      "have the correct title" in {
        gui.title should be("Roulette")
      }

      "initialize components correctly" in {
        gui.contents should not be empty
      }

      "handle update events safely" in {
        // We verify that the update method processes events without throwing exceptions.
        // Assuming GameState(winningNumber: Int, bets: List[Bet]) structure based on usage.
        val mockGameState = GameState(0, List())
        
        noException should be thrownBy gui.update(NewRound(mockGameState))
        noException should be thrownBy gui.update(BetPlaced(mockGameState))
        noException should be thrownBy gui.update(BetUndone)
      }
    }
  }
}