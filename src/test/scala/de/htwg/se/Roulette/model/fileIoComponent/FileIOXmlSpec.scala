package de.htwg.se.Roulette.model.fileIoComponent

import de.htwg.se.Roulette.model.bets.{NumberBet, RedBet}
import de.htwg.se.Roulette.model.fileIoComponent.fileIoXmlImpl.FileIO
import de.htwg.se.Roulette.model.modelImpl.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.{File, PrintWriter}

class FileIOXmlSpec extends AnyWordSpec with Matchers {

  "The XML FileIO implementation" should {
    "save and load a game state correctly" in {
      val fileIo = new FileIO()
      val gameState = GameState(winningNumber = 25, bets = List(RedBet(10), NumberBet(10, 20)))
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
    
    "throw an exception when loading unknown bet type" in {
      val fileIo = new FileIO()
      val pw = new PrintWriter(new File("gameState.xml"))
      pw.write("<gameState><winningNumber>0</winningNumber><balance>100</balance><bets><bet type=\"UnknownBet\"><amount>10</amount></bet></bets></gameState>")
      pw.close()
      
      try {
        an [RuntimeException] should be thrownBy fileIo.load
      } finally {
        new File("gameState.xml").delete()
      }
    }
  }
}