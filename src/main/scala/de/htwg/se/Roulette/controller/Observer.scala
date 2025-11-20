package de.htwg.se.Roulette.controller

trait Observer[E] {
  def update(event: E): Unit
}
