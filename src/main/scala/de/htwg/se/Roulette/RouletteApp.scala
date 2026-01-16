package de.htwg.se.Roulette

import com.google.inject.Guice
import de.htwg.se.Roulette.aview.{ConsoleObserver, SwingGui, Tui}
import de.htwg.se.Roulette.controller.{ControllerInterface, GameController}

object RouletteApp {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new RouletteModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    injector.getInstance(classOf[ConsoleObserver])
    val tui = injector.getInstance(classOf[Tui])
    new Thread(() => tui.run()).start()
    val gui = injector.getInstance(classOf[SwingGui])
    controller.startRound()
  }
}