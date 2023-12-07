package io.github.stoerti.aoc.day1

import io.github.stoerti.aoc.IOUtils

fun main(args: Array<String>) {

  val result = IOUtils.readInput("day_1_input")
    .map {
      it
        .replace("oneight", "18")
        .replace("eightwo", "82")
        .replace("twone", "21")
        .replace("one", "1")
        .replace("two", "2")
        .replace("three", "3")
        .replace("four", "4")
        .replace("five", "5")
        .replace("six", "6")
        .replace("seven", "7")
        .replace("eight", "8")
        .replace("nine", "9")
    }
    .map { it.replace("[a-z]*".toRegex(), "") }
    .sumOf {
      val result = Integer.parseInt(it.first().toString() + it.last())
      println("$it : $result")
      result
    }

  println("result2: $result")
}
