package de.htwg.se.Roulette

import de.htwg.se.Roulette.aview.{ConsoleObserver, SwingGui, Tui}
import de.htwg.se.Roulette.controller.{ControllerInterface, GameController}

object RouletteApp {
  def main(args: Array[String]): Unit = {
    val controller: ControllerInterface = new GameController()
    new ConsoleObserver(controller)
    val tui = new Tui(controller)
    new Thread(() => tui.run()).start()
    val gui = new SwingGui(controller)
    controller.startRound()
  }
}