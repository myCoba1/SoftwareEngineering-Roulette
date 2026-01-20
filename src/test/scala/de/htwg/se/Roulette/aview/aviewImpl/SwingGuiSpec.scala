package de.htwg.se.Roulette.aview.aviewImpl

import de.htwg.se.Roulette.controller.{BetPlaced, BetUndone, ControllerEvent, ControllerInterface, NewRound}
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.bets._
import de.htwg.se.Roulette.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.util.{Success, Try}
import java.awt.GraphicsEnvironment
import scala.swing.{Button, Label, TextField, ToggleButton}

class SwingGuiSpec extends AnyWordSpec with Matchers {

  class MockController extends ControllerInterface {
    var betsPlaced: List[Bet] = List()
    var roundStarted = false
    var undoCalled = false
    var saveCalled = false
    var loadCalled = false

    override def addObserver(o: Observer[ControllerEvent]): Unit = {}
    override def removeObserver(o: Observer[ControllerEvent]): Unit = {}
    override def placeBet(bets: List[Bet]): Unit = { betsPlaced = bets }
    override def startRound(): Unit = { roundStarted = true }
    override def undo(): Try[Unit] = { undoCalled = true; Success(()) }
    override def save(): Unit = { saveCalled = true }
    override def load(): Unit = { loadCalled = true }
    override def gameState: Option[GameStateInterface] = Some(GameState(0, List(), 100))
  }

  "A SwingGui" should {
    if (GraphicsEnvironment.isHeadless) {
      "be skipped in headless environment" in {
        cancel("Running in headless environment")
      }
    } else {
      "have the correct title" in {
        val controller = new MockController
        val gui = new SwingGui(controller)
        gui.title should be("Roulette")
        gui.dispose()
      }

      "initialize components correctly" in {
        val controller = new MockController
        val gui = new SwingGui(controller)
        gui.contents should not be empty
        gui.dispose()
      }

      "update its UI based on controller events" in {
        val controller = new MockController
        val gui = new SwingGui(controller)

        // Test NewRound event
        val newRoundState = GameState(0, List(), 500)
        gui.update(NewRound(newRoundState))
        gui.balanceLabel.text should be("Balance: 500")
        gui.winningNumberLabel.text should be("Place your bets!")
        gui.betsLabel.text should be("Bets: ")
        gui.placeBetButton.enabled should be(true)
        gui.newRoundButton.enabled should be(false)

        // Test BetPlaced event
        val bet = RedBet(10)
        val betPlacedState = GameState(winningNumber = 7, bets = List(bet), balance = 490)
        gui.update(BetPlaced(betPlacedState))
        gui.balanceLabel.text should be("Balance: 490")
        gui.winningNumberLabel.text should be("Winning Number: 7")
        gui.betsLabel.text should be("Bets: Red (10)")
        gui.placeBetButton.enabled should be(false)
        gui.newRoundButton.enabled should be(true)

        // Test BetUndone event
        gui.update(BetUndone)
        gui.balanceLabel.text should be("Balance: 100") // from mock controller's gameState
        gui.winningNumberLabel.text should be("Place your bets!")
        gui.betsLabel.text should be("Bets: ") // mock controller has empty bets list
        gui.placeBetButton.enabled should be(true)
        gui.newRoundButton.enabled should be(false)
        gui.dispose()
      }
      
      "react to buttons" in {
        val controller = new MockController
        val gui = new SwingGui(controller)
        // Access buttons directly (package private)
        val newRoundButton = gui.newRoundButton
        val undoButton = gui.undoButton
        val saveButton = gui.saveButton
        val loadButton = gui.loadButton
        val placeBetButton = gui.placeBetButton
        val redBetButton = gui.redBetButton
        val blackBetButton = gui.blackBetButton
        val evenBetButton = gui.evenBetButton
        val zeroButton = gui.zeroButton
        val numberButtons = gui.numberButtons
        val lineOne = gui.lineOne

        val stakeTextField = gui.stakeTextField
        val allInButton = gui.allInButton

        // Simulate clicks
        newRoundButton.peer.doClick()
        controller.roundStarted should be(true)

        undoButton.peer.doClick()
        controller.undoCalled should be(true)

        saveButton.peer.doClick()
        controller.saveCalled should be(true)

        loadButton.peer.doClick()
        controller.loadCalled should be(true)
        
        // Test All In
        allInButton.peer.doClick()
        stakeTextField.text should be("100") // Balance from mock controller

        // Test Place Bet
        // Select Red
        redBetButton.selected = true
        placeBetButton.peer.doClick()
        controller.betsPlaced should contain(RedBet(100)) // 100 because of All In
        
        // Reset and test Black + Even + Number 1
        redBetButton.selected = false
        blackBetButton.selected = true
        evenBetButton.selected = true
        numberButtons.head.selected = true // Number 1
        stakeTextField.text = "10"
        
        placeBetButton.peer.doClick()
        controller.betsPlaced should contain(BlackBet(10))
        controller.betsPlaced should contain(EvenBet(10))
        controller.betsPlaced should contain(NumberBet(1, 10))

        // Test Zero and Line One
        blackBetButton.selected = false
        evenBetButton.selected = false
        numberButtons.head.selected = false
        zeroButton.selected = true
        lineOne.selected = true
        
        placeBetButton.peer.doClick()
        controller.betsPlaced should contain(NumberBet(0, 10))
        controller.betsPlaced should contain(LineOneBet(10))

        // Test No Bet Selected
        // We avoid clicking placeBetButton with no selection to prevent Dialog popup in tests
        gui.dispose()
      }
    }
  }
}