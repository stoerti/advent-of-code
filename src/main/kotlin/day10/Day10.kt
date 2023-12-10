package io.github.stoerti.aoc.day10

import io.github.stoerti.aoc.Grid2D
import io.github.stoerti.aoc.Grid2D.Coordinate
import io.github.stoerti.aoc.IOUtils

fun main(args: Array<String>) {
  val grid = IOUtils.readInput("day_10_input").let { Grid2D.charGrid(it) }
  println(grid)

  val result1 = part1(grid)

  println("Result1: $result1")
}

fun part1(map: Grid2D<Char>): Int {
  val start = map.findStart()
  println("Start at $start")
  val firstMove = listOf(start.up(), start.down(), start.left(), start.right())
      .first { map.findPathNeighbors(it).contains(start) }

  val path = mutableListOf(start, firstMove)

  while (path.last() != start) {
    path.add(map.findPathNeighbors(path.last()).first { path[path.size-2] != it })
  }

  return path.size / 2
}

fun Grid2D<Char>.findStart(): Coordinate {
  map.forEachIndexed { y, col ->
    col.forEachIndexed { x, point ->
      if (point == 'S') return@findStart Coordinate(
        x,
        y
      )
    }
  }
  throw Exception()
}

fun Grid2D<Char>.findPathNeighbors(coordinate: Coordinate): List<Coordinate> {
  return when (get(coordinate)) {
    '|' -> listOf(coordinate.up(), coordinate.down())
    '-' -> listOf(coordinate.left(), coordinate.right())
    'L' -> listOf(coordinate.up(), coordinate.right())
    'J' -> listOf(coordinate.up(), coordinate.left())
    '7' -> listOf(coordinate.left(), coordinate.down())
    'F' -> listOf(coordinate.right(), coordinate.down())
    '.' -> emptyList()
    null -> emptyList()
    else -> throw Exception("Unknown char ${get(coordinate)} at $coordinate")
  }
}
