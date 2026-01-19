package de.htwg.se.Roulette

import com.google.inject.{Guice, ProvisionException}
import de.htwg.se.Roulette.aview.aviewImpl.{ConsoleObserver, SwingGui}
import de.htwg.se.Roulette.aview.{ConsoleObserverInterface, GuiInterface}
import de.htwg.se.Roulette.controller.ControllerInterface
import de.htwg.se.Roulette.controller.controllerImpl.GameController
import de.htwg.se.Roulette.model.fileIoComponent.FileIOInterface
import de.htwg.se.Roulette.model.fileIoComponent.fileIoXmlImpl.FileIO
import net.codingwell.scalaguice.InjectorExtensions._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.awt.GraphicsEnvironment

class RouletteModuleSpec extends AnyWordSpec with Matchers {
  "A RouletteModule" should {
    "configure bindings correctly" in {
      val injector = Guice.createInjector(new RouletteModule)

      val controller = injector.instance[ControllerInterface]
      controller shouldBe a[GameController]

      // verify singleton scope
      val anotherController = injector.instance[ControllerInterface]
      controller should be theSameInstanceAs anotherController

      val consoleObserver = injector.instance[ConsoleObserverInterface]
      consoleObserver shouldBe a[ConsoleObserver]

      if (!GraphicsEnvironment.isHeadless) {
        val gui = injector.instance[GuiInterface]
        gui shouldBe a[SwingGui]
      } else {
        val ex = intercept[ProvisionException] {
          injector.instance[GuiInterface]
        }
        ex.getCause shouldBe a[java.awt.HeadlessException]
      }

      val fileIo = injector.instance[FileIOInterface]
      fileIo shouldBe a[FileIO]
    }
  }
}