package day2

fun main(args: Array<String>) {
  val redLimit = 12
  val greenLimit = 13
  val blueLimit = 14

  val fileContent = Any::class.java.getResource("day_2_input.txt")!!.readText()

  val games = fileContent.split("\n")
    .map { Game.fromString(it) }

  val result1 = games
    .filter {
      it.maxRed() <= redLimit && it.maxBlue() <= blueLimit && it.maxGreen() <= greenLimit
    }
    .onEach { println("${it.gameId} - maxRed: ${it.maxRed()}, maxBlue: ${it.maxBlue()}, maxGreen: ${it.maxGreen()}, ") }
    .sumOf { it.gameId }

  val result2 = games
    .onEach { println("${it.gameId} - maxRed: ${it.maxRed()}, maxBlue: ${it.maxBlue()}, maxGreen: ${it.maxGreen()}, ") }
    .map {
      it.maxRed() * it.maxBlue() * it.maxGreen()
    }
    .sumOf { it }


  println("Result Puzzle 1: $result1")
  println("Result Puzzle 2: $result2")
}

data class Game(
  val gameId: Int,
  val rounds: List<GameRound>,
) {
  companion object {
    fun fromString(inputLine: String): Game {
      val gameId = inputLine.split(":")[0].removePrefix("Game ").toInt()
      val gameRounds = inputLine.split(":")[1].split(";").map { it.trim() }.map { GameRound.fromString(it) }

      return Game(gameId, gameRounds)
    }
  }

  fun maxRed(): Int = rounds.maxOf { it.red }
  fun maxBlue(): Int = rounds.maxOf { it.blue }
  fun maxGreen(): Int = rounds.maxOf { it.green }
}


data class GameRound(
  val red: Int,
  val blue: Int,
  val green: Int,
) {
  companion object {
    fun fromString(input: String): GameRound {
      val result = input.split(",").map { it.trim() }

      return GameRound(
        red = result.find { it.contains("red") }?.let { it.substring(0, it.indexOf(" ")).toInt() } ?: 0,
        blue = result.find { it.contains("blue") }?.let { it.substring(0, it.indexOf(" ")).toInt() } ?: 0,
        green = result.find { it.contains("green") }?.let { it.substring(0, it.indexOf(" ")).toInt() } ?: 0,
      )
    }
  }
}
