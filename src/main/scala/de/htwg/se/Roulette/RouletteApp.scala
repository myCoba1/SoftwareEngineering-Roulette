package de.htwg.se.Roulette

import com.google.inject.Guice
import de.htwg.se.Roulette.aview.aviewImpl.Tui
import de.htwg.se.Roulette.aview.GuiInterface
import de.htwg.se.Roulette.controller.ControllerInterface

object RouletteApp {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new RouletteModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    val tui = injector.getInstance(classOf[Tui])
    val gui = injector.getInstance(classOf[GuiInterface])

    new Thread(() => tui.run()).start()

    gui.open()

    while (gui.asInstanceOf[swing.MainFrame].visible) {
      Thread.sleep(100)
    }

    controller.startRound()
  }
}