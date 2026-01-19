package de.htwg.se.Roulette.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.model.bets._
import scala.xml.{Node, PrettyPrinter, XML}
import java.io.{File, PrintWriter}

class FileIO extends FileIOInterface {
  override def load: GameStateInterface = {
    val file = XML.loadFile("gameState.xml")
    val winningNumber = (file \ "winningNumber").text.trim.toInt
    val betNodes = (file \ "bets" \ "bet")
    val bets = betNodes.map { node =>
      val betType = (node \ "@type").text
      betType match {
        case "NumberBet" => NumberBet((node \ "value").text.trim.toInt)
        case "RedBet" => RedBet()
        case "BlackBet" => BlackBet()
        case "FirstThirdBet" => FirstThirdBet()
        case "SecondThirdBet" => SecondThirdBet()
        case "ThirdThirdBet" => ThirdThirdBet()
        case "FirstHalfBet" => FirstHalfBet()
        case "SecondHalfBet" => SecondHalfBet()
        case "EvenBet" => EvenBet()
        case "OddBet" => OddBet()
        case "LineOneBet" => LineOneBet()
        case "LineTwoBet" => LineTwoBet()
        case "LineThreeBet" => LineThreeBet()
        case _ => throw new RuntimeException("Unknown Bet Type")
      }
    }.toList
    GameState(winningNumber, bets)
  }

  override def save(gameState: GameStateInterface): Unit = {
    val pw = new PrintWriter(new File("gameState.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = <gameState>
      <winningNumber>{gameState.winningNumber}</winningNumber>
      <bets>
        {gameState.bets.map(betToXml)}
      </bets>
    </gameState>
    pw.write(prettyPrinter.format(xml))
    pw.close()
  }

  private def betToXml(bet: Bet): Node = {
    bet match {
      case NumberBet(n) => <bet type="NumberBet"><value>{n}</value></bet>
      case _ => <bet type={bet.getClass.getSimpleName.replace("$", "")}/>
    }
  }
}