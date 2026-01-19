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
    val balance = (file \ "balance").text.trim.toInt
    val betNodes = (file \ "bets" \ "bet")
    val bets = betNodes.map { node =>
      val betType = (node \ "@type").text
      val amount = (node \ "amount").text.trim.toInt
      betType match {
        case "NumberBet" => NumberBet((node \ "value").text.trim.toInt, amount)
        case "RedBet" => RedBet(amount)
        case "BlackBet" => BlackBet(amount)
        case "FirstThirdBet" => FirstThirdBet(amount)
        case "SecondThirdBet" => SecondThirdBet(amount)
        case "ThirdThirdBet" => ThirdThirdBet(amount)
        case "FirstHalfBet" => FirstHalfBet(amount)
        case "SecondHalfBet" => SecondHalfBet(amount)
        case "EvenBet" => EvenBet(amount)
        case "OddBet" => OddBet(amount)
        case "LineOneBet" => LineOneBet(amount)
        case "LineTwoBet" => LineTwoBet(amount)
        case "LineThreeBet" => LineThreeBet(amount)
        case _ => throw new RuntimeException("Unknown Bet Type")
      }
    }.toList
    GameState(winningNumber, bets, balance)
  }

  override def save(gameState: GameStateInterface): Unit = {
    val pw = new PrintWriter(new File("gameState.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = <gameState>
      <winningNumber>{gameState.winningNumber}</winningNumber>
      <balance>{gameState.balance}</balance>
      <bets>
        {gameState.bets.map(betToXml)}
      </bets>
    </gameState>
    pw.write(prettyPrinter.format(xml))
    pw.close()
  }

  private def betToXml(bet: Bet): Node = {
    bet match {
      case NumberBet(n, amount) => <bet type="NumberBet"><value>{n}</value><amount>{amount}</amount></bet>
      case _ => <bet type={bet.getClass.getSimpleName.replace("$", "")}><amount>{bet.amount}</amount></bet>
    }
  }
}