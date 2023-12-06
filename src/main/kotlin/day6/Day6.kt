package day5

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main(args: Array<String>) {
  val lines = object {}.javaClass.getResourceAsStream("../day_6_input.txt")!!
    .bufferedReader()
    .readLines().toMutableList().also { it.add("") }

  val raceTimes = lines[0].removePrefix("Time:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
  val distancesToBeat = lines[1].removePrefix("Distance:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }

  val races = raceTimes.mapIndexed { index, time -> Race(time, distancesToBeat[index])}

  val result1 = races.onEach { println("$it: ${it.lowestButtonDuration()} to ${it.highestButtonDuration()} -> ${it.numberOfPossibilities()}") }
    .map { it.numberOfPossibilities() }
    .reduce { acc, i -> acc * i }

  val result2 = races.reduce { acc,i -> Race("${acc.raceTime}${i.raceTime}".toLong(), "${acc.distanceToBeat}${i.distanceToBeat}".toLong()) }.numberOfPossibilities()


  println("Result 1: $result1")
  println("Result 2: $result2")
}

data class Race(
  val raceTime: Long,
  val distanceToBeat: Long,
) {
  fun lowestButtonDuration() : Long {
    val p = raceTime.toDouble()
    val q = distanceToBeat.toDouble()+1

    return ceil((p/2) - sqrt((p*p)/4 - q)).toLong()
  }
  fun highestButtonDuration() : Long {
    val p = raceTime.toDouble()
    val q = distanceToBeat.toDouble()+1

    return floor((p/2) + sqrt((p*p)/4 - q)).toLong()
  }

  fun numberOfPossibilities() : Long {
    return highestButtonDuration() - lowestButtonDuration() + 1
  }
}

