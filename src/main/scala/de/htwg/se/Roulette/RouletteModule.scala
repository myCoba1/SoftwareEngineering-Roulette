package de.htwg.se.Roulette

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.controller.ControllerInterface
import de.htwg.se.Roulette.aview.{ConsoleObserverInterface, GuiInterface}
import de.htwg.se.Roulette.aview.aviewImpl.{ConsoleObserver, SwingGui}
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.fileIoComponent.fileIoXmlImpl.FileIO

class RouletteModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ControllerInterface].to[GameController].asEagerSingleton()
    bind[ConsoleObserverInterface].to[ConsoleObserver]
    bind[GuiInterface].to[SwingGui]
    bind[FileIOInterface].to[FileIO]
  }
}