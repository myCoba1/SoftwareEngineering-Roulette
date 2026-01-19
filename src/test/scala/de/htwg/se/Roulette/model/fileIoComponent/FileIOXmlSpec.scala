package de.htwg.se.Roulette.model.fileIoComponent

import de.htwg.se.Roulette.model.bets.{NumberBet, RedBet}
import de.htwg.se.Roulette.model.fileIoComponent.fileIoXmlImpl.FileIO
import de.htwg.se.Roulette.model.modelImpl.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.File

class FileIOXmlSpec extends AnyWordSpec with Matchers {

  "The XML FileIO implementation" should {
    "save and load a game state correctly" in {
      val fileIo = new FileIO()
      val gameState = GameState(winningNumber = 25, bets = List(RedBet(), NumberBet(10)))
      val file = new File("gameState.xml")

      if (file.exists()) file.delete()

      try {
        fileIo.save(gameState)
        file.exists() should be(true)

        val loadedGameState = fileIo.load

        loadedGameState.winningNumber should be(gameState.winningNumber)
        loadedGameState.bets should contain theSameElementsAs gameState.bets
      } finally {
        if (file.exists()) file.delete()
      }
    }
  }
}