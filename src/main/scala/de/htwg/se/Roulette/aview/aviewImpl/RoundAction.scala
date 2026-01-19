package de.htwg.se.Roulette.aview.aviewImpl

sealed trait RoundAction
case object Continue extends RoundAction
case object Undo extends RoundAction
case object Quit extends RoundAction