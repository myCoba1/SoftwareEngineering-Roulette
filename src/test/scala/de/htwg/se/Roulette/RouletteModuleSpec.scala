package de.htwg.se.Roulette

import com.google.inject.Guice
import de.htwg.se.Roulette.aview.ConsoleObserverInterface
import de.htwg.se.Roulette.controller.ControllerInterface
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RouletteModuleSpec extends AnyWordSpec with Matchers {
  "A RouletteModule" should {
    "configure the injector correctly" in {
      val injector = Guice.createInjector(new RouletteModule)
      injector.getInstance(classOf[ControllerInterface]) should not be null
      injector.getInstance(classOf[ConsoleObserverInterface]) should not be null
    }
  }
}