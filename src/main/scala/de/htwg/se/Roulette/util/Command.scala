package de.htwg.se.Roulette.util

import scala.util.Try

trait Command {
  def execute(): Try[Unit]
  def undo(): Try[Unit]
}