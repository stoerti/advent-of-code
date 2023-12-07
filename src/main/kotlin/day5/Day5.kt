package io.github.stoerti.aoc.day5

import io.github.stoerti.aoc.IOUtils
import io.github.stoerti.aoc.StringExt.longValues
import java.util.ListResourceBundle
import java.util.Locale

fun main(args: Array<String>) {
  val lines = IOUtils.readInput("day_5_input").toMutableList().also { it.add("") }

  val seeds = lines.removeAt(0).substring(7).longValues()
  lines.removeAt(0) // remove next empty line too

  val mappings = mutableMapOf<String, Mapping>()

  val mappingBuffer = mutableListOf<String>()
  var mappingName = ""

  while (lines.isNotEmpty()) {
    val line = lines.removeAt(0)

    if (line.contains("map:")) {
      mappingName = line.removeSuffix(" map:")
    } else if (line.isBlank()) {
      mappings[mappingName] = Mapping.fromString(mappingBuffer)
      mappingBuffer.clear()
    } else {
      mappingBuffer.add(line)
    }
  }

  println(mappings)

  val result1 = seeds.map { seed -> seed
    .let { mappings["seed-to-soil"]!!.map(it) }
    .let { mappings["soil-to-fertilizer"]!!.map(it) }
    .let { mappings["fertilizer-to-water"]!!.map(it) }
    .let { mappings["water-to-light"]!!.map(it) }
    .let { mappings["light-to-temperature"]!!.map(it) }
    .let { mappings["temperature-to-humidity"]!!.map(it) }
    .let { mappings["humidity-to-location"]!!.map(it) }
  }.also { println(it) }.min()

  println("Result 1: $result1")
}

data class Mapping(
  val mappings: List<MappingPart>
) {
  companion object {
    fun fromString(lineBlock: List<String>): Mapping =
      Mapping(lineBlock.map {
        val destStart = it.split(" ")[0].toLong()
        val sourceStart = it.split(" ")[1].toLong()
        val rangeSize = it.split(" ")[2].toLong()

        MappingPart(sourceStart, destStart, rangeSize)
      })
  }

  fun map(source: Long) : Long {
    mappings.find { it.matches(source) }?.let { return it.map(source) }

    return source
  }
}

data class MappingPart(
  val sourceStart: Long,
  val destStart: Long,
  val size: Long,
) {
  fun matches(source: Long) : Boolean {
    return sourceStart <= source && source < sourceStart + size
  }

  fun map(source: Long) : Long {
    return source + destStart - sourceStart
  }
}
