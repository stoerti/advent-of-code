package io.github.stoerti.aoc.day8

import io.github.stoerti.aoc.IOUtils
import io.github.stoerti.aoc.MathUtils
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main(args: Array<String>) {
  val lines = IOUtils.readInput("day_8_input")

  val directions = lines[0]
  val mapLines = lines.subList(2, lines.size)

  val map = mapLines.associate { it.substring(0, 3) to Paths(it.substring(7, 10), it.substring(12, 15)) }

  println("Result1: ${part1(directions, map)}")
  println("Result2: ${part2(directions, map)}")
}

fun part1(directions: String, map: Map<String, Paths>) : Long {
  var currentNode = "AAA"
  var steps = 0L
  while (currentNode != "ZZZ") {
    directions.toCharArray().forEach {
      if (map[currentNode] == null) println("Node $currentNode does not exist")
      currentNode = map[currentNode]!!.let { path -> if (it == 'L') path.left else path.right }
      steps++
    }
  }

  return steps
}


fun part2(directions: String, map: Map<String, Paths>) : Long {
  val allANodes = map.filterKeys { it.endsWith('A') }
  val allCurrentNodes = allANodes.keys.associateWith { it }.toMutableMap()
  val allNodeSteps = allANodes.keys.associateWith { 0L }.toMutableMap()
  allCurrentNodes.keys.forEach { node ->
    var steps = 0L
    var currentNode = node
    while (!currentNode.endsWith('Z')) {
      directions.toCharArray().forEach { direction ->
        currentNode = map[currentNode]!!.let { path -> if (direction == 'L') path.left else path.right }
        steps++
      }
    }
    allNodeSteps[node] = steps
  }
  return MathUtils.getLeastCommonMultiple(allNodeSteps.values)
}

data class Paths(val left: String, val right: String)
