package de.htwg.se.Roulette.aview.aviewImpl

import com.google.inject.Inject
import de.htwg.se.Roulette.aview.GuiInterface
import de.htwg.se.Roulette.controller.{BetPlaced,BetUndone,ControllerEvent,ControllerInterface,NewRound}
import de.htwg.se.Roulette.model.bets.*
import de.htwg.se.Roulette.util.Observer

import java.awt.event.{ItemEvent, ItemListener}
import java.awt.{Color, Dimension}
import javax.swing.BorderFactory
import scala.swing.*
import scala.swing.event.ButtonClicked

class SwingGui @Inject() (controller: ControllerInterface) extends MainFrame 
  with Observer[ControllerEvent] with GuiInterface {
  controller.addObserver(this)

  title = "Roulette"
  private[aviewImpl] val winningNumberLabel = new Label("Winning Number: -")
  private[aviewImpl] val betsLabel = new Label("Bets: ")
  private[aviewImpl] val balanceLabel = new Label("Balance: 100")
  private var currentBalance: Int = 100
  private[aviewImpl] val stakeTextField = new TextField("10") { columns = 5 }
  private[aviewImpl] val allInButton = new Button("All In")
  private[aviewImpl] val placeBetButton = new Button("Place Bet")
  private[aviewImpl] val newRoundButton = new Button("New Round")
  private[aviewImpl] val undoButton = new Button("Undo")
  private[aviewImpl] val saveButton = new Button("Save")
  private[aviewImpl] val loadButton = new Button("Load")
  private[aviewImpl] val quitButton = new Button("Quit")

  private object ButtonFactory {
    def createBetButton(text: String, bg: Color, fg: Color = Color.BLACK): ToggleButton = {
      new ToggleButton(text) {
        preferredSize = new Dimension(100, 50)
        background = bg
        foreground = fg
        opaque = true
        contentAreaFilled = true
        focusPainted = false
        border = BorderFactory.createLineBorder(Color.WHITE, 1)
        peer.addItemListener(new ItemListener {
          override def itemStateChanged(e: ItemEvent): Unit = {
            if (selected) {
              background = Color.BLUE
              foreground = Color.WHITE
            } else {
              background = bg
              foreground = fg
            }
          }
        })
      }
    }

    def createZeroButton(): ToggleButton = {
      val btn = createBetButton("0", Color.GREEN)
      btn.preferredSize = new Dimension(150, 50)
      btn.maximumSize = new Dimension(Int.MaxValue, 50)
      btn
    }

    def createLineButton(): ToggleButton ={
      val btn = createBetButton("2-1", Color.LIGHT_GRAY)
      btn.preferredSize = new Dimension(50, 50)
      btn.maximumSize = new Dimension(Int.MaxValue, 50)
      btn
    }

    def createNumberButton(number: Int): ToggleButton = {
      val c = RedBlack.colorOf(number)
      val bg = if (c == 'R') Color.RED else Color.BLACK
      val btn = createBetButton(number.toString, bg, Color.WHITE)
      btn.preferredSize = new Dimension(50, 50)
      btn
    }
  }

  private[aviewImpl] val redBetButton = ButtonFactory.createBetButton("RED", Color.RED, Color.WHITE)
  private[aviewImpl] val blackBetButton = ButtonFactory.createBetButton("BLACK", Color.BLACK, Color.WHITE)
  private[aviewImpl] val lowBetButton = ButtonFactory.createBetButton("1-18", Color.LIGHT_GRAY)
  private[aviewImpl] val highBetButton = ButtonFactory.createBetButton("19-36", Color.LIGHT_GRAY)
  private[aviewImpl] val firstDozenBetButton = ButtonFactory.createBetButton("1-12", Color.LIGHT_GRAY)
  private[aviewImpl] val secondDozenBetButton = ButtonFactory.createBetButton("13-24", Color.LIGHT_GRAY)
  private[aviewImpl] val thirdDozenBetButton = ButtonFactory.createBetButton("25-36", Color.LIGHT_GRAY)
  private[aviewImpl] val evenBetButton = ButtonFactory.createBetButton("EVEN", Color.LIGHT_GRAY)
  private[aviewImpl] val oddBetButton = ButtonFactory.createBetButton("ODD", Color.LIGHT_GRAY)

  private val specialBetButtons = List(redBetButton, blackBetButton, lowBetButton, highBetButton, firstDozenBetButton, 
                                        secondDozenBetButton, thirdDozenBetButton, evenBetButton, oddBetButton)

  private[aviewImpl] val zeroButton = ButtonFactory.createZeroButton()

  private[aviewImpl] val numberButtons = (1 to 36).map(ButtonFactory.createNumberButton)

  private[aviewImpl] val lineOne = ButtonFactory.createLineButton()
  private[aviewImpl] val lineTwo = ButtonFactory.createLineButton()
  private[aviewImpl] val lineThree = ButtonFactory.createLineButton()
  private[aviewImpl] val lineButtons = List(lineOne, lineTwo, lineThree)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel(winningNumberLabel)
    contents += new FlowPanel(betsLabel)
    contents += new FlowPanel(balanceLabel)
    contents += new FlowPanel(new Label("Stake:"), stakeTextField, allInButton)
    contents += new FlowPanel(placeBetButton)

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BorderPanel {
        layout(zeroButton) = BorderPanel.Position.North
        layout(new GridPanel(12, 3) {
          hGap = 0
          vGap = 0
          numberButtons.foreach(contents += _)
        }) = BorderPanel.Position.Center
        layout(new GridPanel(1, 3) {
          hGap = 0
          vGap = 0
          contents ++= List(lineOne, lineTwo, lineThree)
        }) = BorderPanel.Position.South
      }

      contents += Swing.HStrut(10)
      
      // First column
      contents += new GridPanel(3, 1) {
        contents ++= List(firstDozenBetButton, secondDozenBetButton, thirdDozenBetButton)
        preferredSize = new Dimension(100, 600) // Height matches number grid (12 * 50)
      }
      
      // Second column
      contents += new GridPanel(6, 1) {
        contents ++= List(lowBetButton,evenBetButton, redBetButton, blackBetButton, oddBetButton, highBetButton)
        preferredSize = new Dimension(100, 600)
      }
    }

    contents += new FlowPanel(newRoundButton, undoButton, saveButton, loadButton, quitButton)
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  listenTo(placeBetButton, newRoundButton, undoButton, quitButton, allInButton, saveButton, loadButton)

  reactions += {
    case ButtonClicked(`allInButton`) =>
      stakeTextField.text = currentBalance.toString
    case ButtonClicked(`placeBetButton`) =>
      stakeTextField.text.toIntOption match {
        case Some(stake) if stake > 0 && stake <= currentBalance =>
          val selectedNumbers = (if (zeroButton.selected) List(0) else Nil) ++
            numberButtons.filter(_.selected).map(_.text.toInt)

          val numberBets = selectedNumbers.map(n => de.htwg.se.Roulette.model.bets.NumberBet(n, stake))
          val specialBets = specialBetButtons.filter(_.selected).flatMap {
            case `lowBetButton` => Some(FirstHalfBet(stake))
            case `highBetButton` => Some(SecondHalfBet(stake))
            case `firstDozenBetButton` => Some(FirstThirdBet(stake))
            case `secondDozenBetButton` => Some(SecondThirdBet(stake))
            case `thirdDozenBetButton` => Some(ThirdThirdBet(stake))
            case `redBetButton` => Some(RedBet(stake))
            case `blackBetButton` => Some(BlackBet(stake))
            case `evenBetButton` => Some(EvenBet(stake))
            case `oddBetButton` => Some(OddBet(stake))
          }
          val lineBets = List(
            if (lineOne.selected) Some(LineOneBet(stake)) else None,
            if (lineTwo.selected) Some(LineTwoBet(stake)) else None,
            if (lineThree.selected) Some(LineThreeBet(stake)) else None
          ).flatten
          val allBets = numberBets ++ specialBets ++ lineBets

          if (allBets.nonEmpty) {
            controller.placeBet(allBets)
            zeroButton.selected = false
            numberButtons.foreach(_.selected = false)
            specialBetButtons.foreach(_.selected = false)
            lineButtons.foreach(_.selected = false)
          } else {
            Dialog.showMessage(contents.head, "No number selected.", "Error")
          }
        case Some(stake) if stake <= 0 =>
          Dialog.showMessage(contents.head, "Stake must be positive.", "Error")
        case Some(stake) if stake > currentBalance =>
          Dialog.showMessage(contents.head, s"You only have $currentBalance.", "Error")
        case None =>
          Dialog.showMessage(contents.head, "Please enter a valid integer stake.", "Error")
      }
    case ButtonClicked(`newRoundButton`) =>
      controller.startRound()
    case ButtonClicked(`undoButton`) =>
      controller.undo()
    case ButtonClicked(`saveButton`) =>
      controller.save()
      Dialog.showMessage(contents.head, "Game Saved.", "Info")
    case ButtonClicked(`loadButton`) =>
      controller.load()
    case ButtonClicked(`quitButton`) =>
      sys.exit(0)
  }

  override def update(event: ControllerEvent): Unit = {
    event match {
      case NewRound(gs) =>
        currentBalance = gs.balance
        winningNumberLabel.text = "Place your bets!"
        betsLabel.text = "Bets: "
        balanceLabel.text = s"Balance: ${gs.balance}"
        placeBetButton.enabled = true
        newRoundButton.enabled = false
        zeroButton.enabled = true
        numberButtons.foreach(_.enabled = true)
        specialBetButtons.foreach(_.enabled = true)
        lineButtons.foreach(_.enabled = true)
      case BetPlaced(gs) =>
        currentBalance = gs.balance
        winningNumberLabel.text = s"Winning Number: ${gs.winningNumber}"
        betsLabel.text = s"Bets: ${gs.bets.mkString(", ")}"
        balanceLabel.text = s"Balance: ${gs.balance}"
        if (gs.balance <= 0) {
          Dialog.showMessage(contents.head, "Game Over! You ran out of money.", "Game Over")
        }
        placeBetButton.enabled = false
        newRoundButton.enabled = true
        zeroButton.enabled = false
        numberButtons.foreach(_.enabled = false)
        specialBetButtons.foreach(_.enabled = false)
        lineButtons.foreach(_.enabled = false)
      case BetUndone =>
        controller.gameState match {
          case Some(gs) =>
            currentBalance = gs.balance
            winningNumberLabel.text = "Place your bets!"
            betsLabel.text = s"Bets: ${gs.bets.mkString(", ")}"
            balanceLabel.text = s"Balance: ${gs.balance}"
            placeBetButton.enabled = true
            newRoundButton.enabled = false
            zeroButton.enabled = true
            numberButtons.foreach(_.enabled = true)
            specialBetButtons.foreach(_.enabled = true)
            lineButtons.foreach(_.enabled = true)
          case None =>
        }
    }
  }

  override def open(): Unit = {
    visible = true
  }

  pack()
  centerOnScreen()
}