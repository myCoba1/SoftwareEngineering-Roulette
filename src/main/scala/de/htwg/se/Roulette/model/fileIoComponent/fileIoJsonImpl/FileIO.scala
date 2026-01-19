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
    val bets = (json \ "bets").as[JsArray].value.map { betJson =>
      (betJson \ "type").as[String] match {
        case "NumberBet" => NumberBet((betJson \ "value").as[Int])
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
    val betsJson = JsArray(gameState.bets.map {
      case NumberBet(n) => Json.obj("type" -> "NumberBet", "value" -> n)
      case other => Json.obj("type" -> other.getClass.getSimpleName.replace("$", ""))
    }.toIndexedSeq)

    val json = Json.obj(
      "winningNumber" -> gameState.winningNumber,
      "bets" -> betsJson
    )

    val pw = new PrintWriter(new File("gameState.json"))
    pw.write(Json.prettyPrint(json))
    pw.close()
  }
}