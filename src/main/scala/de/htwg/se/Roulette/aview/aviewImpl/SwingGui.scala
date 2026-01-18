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
  private val winningNumberLabel = new Label("Winning Number: -")
  private val betsLabel = new Label("Bets: ")
  private val placeBetButton = new Button("Place Bet")
  private val newRoundButton = new Button("New Round")
  private val undoButton = new Button("Undo")
  private val quitButton = new Button("Quit")

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

  private val redBetButton = ButtonFactory.createBetButton("RED", Color.RED, Color.WHITE)
  private val blackBetButton = ButtonFactory.createBetButton("BLACK", Color.BLACK, Color.WHITE)
  private val lowBetButton = ButtonFactory.createBetButton("1-18", Color.LIGHT_GRAY)
  private val highBetButton = ButtonFactory.createBetButton("19-36", Color.LIGHT_GRAY)
  private val firstDozenBetButton = ButtonFactory.createBetButton("1-12", Color.LIGHT_GRAY)
  private val secondDozenBetButton = ButtonFactory.createBetButton("13-24", Color.LIGHT_GRAY)
  private val thirdDozenBetButton = ButtonFactory.createBetButton("25-36", Color.LIGHT_GRAY)
  private val evenBetButton = ButtonFactory.createBetButton("EVEN", Color.LIGHT_GRAY)
  private val oddBetButton = ButtonFactory.createBetButton("ODD", Color.LIGHT_GRAY)

  private val specialBetButtons = List(redBetButton, blackBetButton, lowBetButton, highBetButton, firstDozenBetButton, 
                                        secondDozenBetButton, thirdDozenBetButton, evenBetButton, oddBetButton)

  private val zeroButton = ButtonFactory.createZeroButton()

  private val numberButtons = (1 to 36).map(ButtonFactory.createNumberButton)

  private val lineOne = ButtonFactory.createLineButton()
  private val lineTwo = ButtonFactory.createLineButton()
  private val lineThree = ButtonFactory.createLineButton()
  private val lineButtons = List(lineOne, lineTwo, lineThree)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel(winningNumberLabel)
    contents += new FlowPanel(betsLabel)
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

    contents += new FlowPanel(newRoundButton, undoButton, quitButton)
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  listenTo(placeBetButton, newRoundButton, undoButton, quitButton)

  reactions += {
    case ButtonClicked(`placeBetButton`) =>
      val selectedNumbers = (if (zeroButton.selected) List(0) else Nil) ++
        numberButtons.filter(_.selected).map(_.text.toInt)

      val numberBets = selectedNumbers.map(de.htwg.se.Roulette.model.bets.NumberBet.apply)
      val specialBets = specialBetButtons.filter(_.selected).flatMap {
        case `lowBetButton` => Some(FirstHalfBet())
        case `highBetButton` => Some(SecondHalfBet())
        case `firstDozenBetButton` => Some(FirstThirdBet())
        case `secondDozenBetButton` => Some(SecondThirdBet())
        case `thirdDozenBetButton` => Some(ThirdThirdBet())
        case `redBetButton` => Some(RedBet())
        case `blackBetButton` => Some(BlackBet())
        case `evenBetButton` => Some(EvenBet())
        case `oddBetButton` => Some(OddBet())
      }
      val lineBets = List(
        if (lineOne.selected) Some(LineOneBet()) else None,
        if (lineTwo.selected) Some(LineTwoBet()) else None,
        if (lineThree.selected) Some(LineThreeBet()) else None
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
    case ButtonClicked(`newRoundButton`) =>
      controller.startRound()
    case ButtonClicked(`undoButton`) =>
      controller.undo()
    case ButtonClicked(`quitButton`) =>
      sys.exit(0)
  }

  override def update(event: ControllerEvent): Unit = {
    event match {
      case NewRound(gs) =>
        winningNumberLabel.text = "Place your bets!"
        betsLabel.text = "Bets: "
        placeBetButton.enabled = true
        newRoundButton.enabled = false
        zeroButton.enabled = true
        numberButtons.foreach(_.enabled = true)
        specialBetButtons.foreach(_.enabled = true)
        lineButtons.foreach(_.enabled = true)
      case BetPlaced(gs) =>
        winningNumberLabel.text = s"Winning Number: ${gs.winningNumber}"
        betsLabel.text = s"Bets: ${gs.bets.mkString(", ")}"
        placeBetButton.enabled = false
        newRoundButton.enabled = true
        zeroButton.enabled = false
        numberButtons.foreach(_.enabled = false)
        specialBetButtons.foreach(_.enabled = false)
        lineButtons.foreach(_.enabled = false)
      case BetUndone =>
        controller.gameState match {
          case Some(gs) =>
            winningNumberLabel.text = "Place your bets!"
            betsLabel.text = s"Bets: ${gs.bets.mkString(", ")}"
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