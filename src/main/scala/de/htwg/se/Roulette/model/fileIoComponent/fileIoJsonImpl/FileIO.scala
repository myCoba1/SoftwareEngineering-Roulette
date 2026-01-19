package de.htwg.se.Roulette.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.GameStateInterface
import de.htwg.se.Roulette.model.modelImpl.GameState
import de.htwg.se.Roulette.model.bets._
import play.api.libs.json._
import scala.io.Source
import java.io.{File, PrintWriter}

class FileIO extends FileIOInterface {
  override def load: GameStateInterface = {
    val source = Source.fromFile("gameState.json")
    val jsonString = try source.mkString finally source.close()
    val json: JsValue = Json.parse(jsonString)
    val winningNumber = (json \ "winningNumber").as[Int]
    val balance = (json \ "balance").as[Int]
    val bets = (json \ "bets").as[JsArray].value.map { betJson =>
      val amount = (betJson \ "amount").as[Int]
      (betJson \ "type").as[String] match {
        case "NumberBet" => NumberBet((betJson \ "value").as[Int], amount)
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
    val betsJson = JsArray(gameState.bets.map {
      case NumberBet(n, amount) => Json.obj("type" -> "NumberBet", "value" -> n, "amount" -> amount)
      case other => Json.obj("type" -> other.getClass.getSimpleName.replace("$", ""), "amount" -> other.amount)
    }.toIndexedSeq)

    val json = Json.obj(
      "winningNumber" -> gameState.winningNumber,
      "balance" -> gameState.balance,
      "bets" -> betsJson
    )

    val pw = new PrintWriter(new File("gameState.json"))
    pw.write(Json.prettyPrint(json))
    pw.close()
  }
}