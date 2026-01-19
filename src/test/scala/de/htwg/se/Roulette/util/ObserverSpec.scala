package de.htwg.se.Roulette.util

import de.htwg.se.Roulette.util.Observer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ObserverSpec extends AnyWordSpec {
  "An Observer" should {
    "be extendable" in {
      var updated = false
      val observer = new Observer[Boolean] {
        override def update(event: Boolean): Unit = updated = event
      }
      observer.update(true)
      updated should be(true)
    }
  }
}
