package io.github.stoerti.aoc.day9

import io.github.stoerti.aoc.IOUtils
import io.github.stoerti.aoc.MathUtils
import io.github.stoerti.aoc.StringExt.longValues
import kotlin.math.abs

fun main(args: Array<String>) {
  val lines = IOUtils.readInput("day_9_input").map { it.longValues() }

  val result1 = lines.sumOf { part1(it) }
  val result2 = lines.sumOf { part2(it) }

  println("Result1: $result1")
  println("Result2: $result2")
}

fun part1(numbers: List<Long>): Long {
  val lines = mutableListOf(numbers)

  while (!lines.last().onlyZeros()) {
    lines.last().let { line ->
      lines.add(line.mapIndexedNotNull { i, it ->
        if (i >= line.size - 1) null
        else (line[i + 1] - it)
      })
    }
  }

  return lines.sumOf { it.last() }
}
fun part2(numbers: List<Long>): Long {
  val lines = mutableListOf(numbers)

  while (!lines.last().onlyZeros()) {
    lines.last().let { line ->
      lines.add(line.mapIndexedNotNull { i, it ->
        if (i >= line.size - 1) null
        else (line[i + 1] - it)
      })
    }
  }

  var result = 0L
  lines.reversed().forEach {
    result = it.first() - result
  }

  return result
}

fun List<Long>.onlyZeros(): Boolean = this.none { it != 0L }
