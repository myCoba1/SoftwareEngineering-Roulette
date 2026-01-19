package de.htwg.se.Roulette.aview.aviewImpl

import com.google.inject.Inject
import de.htwg.se.Roulette.controller.ControllerInterface
import de.htwg.se.Roulette.model.bets.Bet

import scala.io.StdIn

class Tui @Inject() (controller: ControllerInterface) {
  var currentStake: Int = 10
  def run(): Unit = {
    var input: String = ""
    while ({input = StdIn.readLine(); input != null}) {
      processInput(input)
    }
  }

  def processInput(input: String): Unit = {
    input.trim.toLowerCase.split("\\s+").toList match {
      case "new" :: Nil => controller.startRound()
      case "undo" :: Nil => controller.undo()
      case "save" :: Nil => controller.save()
      case "load" :: Nil => controller.load()
      case "quit" :: Nil => sys.exit(0)
      case "stake" :: amount :: Nil =>
        amount.toIntOption match {
          case Some(a) if a > 0 => currentStake = a; println(s"Stake set to $currentStake")
          case _ => println("Invalid stake amount.")
        }
      case betStrings =>
        val bets = Bet(betStrings.mkString(" "), currentStake)
        if (bets.nonEmpty) {
          controller.placeBet(bets)
        } else {
          println("Invalid command or bet.")
        }
    }
  }
}