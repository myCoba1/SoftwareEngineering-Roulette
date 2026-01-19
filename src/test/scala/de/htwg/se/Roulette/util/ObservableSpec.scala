package de.htwg.se.Roulette.util

import de.htwg.se.Roulette.util.{Observable, Observer}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ObservableSpec extends AnyWordSpec {
  "An Observable" should {
    "add an observer" in {
      val observable = new Observable[Boolean] {}
      val observer = new Observer[Boolean] {
        override def update(event: Boolean): Unit = {}
      }
      observable.addObserver(observer)
    }

    "remove an observer" in {
      val observable = new Observable[Boolean] {}
      val observer = new Observer[Boolean] {
        override def update(event: Boolean): Unit = {}
      }
      observable.addObserver(observer)
      observable.removeObserver(observer)
    }

    "notify observers" in {
      var updated = false
      class TestObservable extends Observable[Boolean] {
        def fire(): Unit = notifyObservers(true)
      }
      val observable = new TestObservable()
      val observer = new Observer[Boolean] {
        override def update(event: Boolean): Unit = updated = event
      }
      observable.addObserver(observer)
      observable.fire()
      updated should be(true)
    }
  }
}
