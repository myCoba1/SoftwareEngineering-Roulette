package de.htwg.se.Roulette.util

trait Observer[E] {
  def update(event: E): Unit
}
