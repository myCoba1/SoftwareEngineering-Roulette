package de.htwg.se.Roulette.model.fileIoComponent

import de.htwg.se.Roulette.model.bets._
import de.htwg.se.Roulette.model.fileIoComponent.fileIoJsonImpl.FileIO
import de.htwg.se.Roulette.model.modelImpl.GameState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.{File, PrintWriter}

class FileIOJsonSpec extends AnyWordSpec with Matchers {

  "The JSON FileIO implementation" should {
    "save and load a game state correctly" in {
      val fileIo = new FileIO()
      val gameState = GameState(winningNumber = 15, bets = List(BlackBet(), NumberBet(22)))
      val file = new File("gameState.json")

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

    "handle all bet types correctly" in {
      val fileIo = new FileIO()
      val allBetsState = GameState(0, List(
        NumberBet(1),
        RedBet(),
        BlackBet(),
        FirstThirdBet(),
        SecondThirdBet(),
        ThirdThirdBet(),
        FirstHalfBet(),
        SecondHalfBet(),
        EvenBet(),
        OddBet(),
        LineOneBet(),
        LineTwoBet(),
        LineThreeBet()
      ))
      fileIo.save(allBetsState)
      val loaded = fileIo.load
      loaded.bets should contain theSameElementsAs allBetsState.bets
      new File("gameState.json").delete()
    }

    "throw an exception when loading unknown bet type" in {
      val fileIo = new FileIO()
      val pw = new PrintWriter(new File("gameState.json"))
      pw.write("""{"winningNumber":0,"bets":[{"type":"UnknownBet","value":0}]}""")
      pw.close()
      an [RuntimeException] should be thrownBy fileIo.load
      new File("gameState.json").delete()
    }
  }
}