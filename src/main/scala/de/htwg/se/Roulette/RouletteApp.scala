package de.htwg.se.Roulette

import com.google.inject.Guice
import de.htwg.se.Roulette.aview.aviewImpl.Tui
import de.htwg.se.Roulette.aview.{ConsoleObserverInterface, GuiInterface}
import de.htwg.se.Roulette.controller.ControllerInterface

object RouletteApp {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new RouletteModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    injector.getInstance(classOf[ConsoleObserverInterface])
    val tui = injector.getInstance(classOf[Tui])
    new Thread(() => tui.run()).start()
    val gui = injector.getInstance(classOf[GuiInterface])
    gui.open()
    controller.startRound()
  }
}