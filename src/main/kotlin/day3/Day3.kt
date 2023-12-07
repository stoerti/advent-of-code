package io.github.stoerti.aoc.day3

import io.github.stoerti.aoc.IOUtils

fun main(args: Array<String>) {
  val schematics = Schematics(IOUtils.readInput("day_3_input"))

  val result1 = schematics.lines.mapIndexed { index, _ -> schematics.scanForPartNumbers(index) }
    .onEach { println(it) }
    .map { it.sumOf { it } }
    .sumOf { it }

  println("Result 1: $result1")
}

data class Schematics(
  val lines: List<String>,
) {

  val symbols = listOf('!', '$', '%')

  fun scanForPartNumbers(lineNumber: Int) : List<Int> {
    val line = lines[lineNumber].asSequence().toMutableList()

    var isScanningNumber = false
    var curDigitString = ""
    val partNumbers = ArrayList<String>()

    for (curIdx in 0..<line.size) {
      val curChar = line[curIdx]
      if (curChar.isDigit()) {
        isScanningNumber = true
        curDigitString += curChar
      } else {
        if (isScanningNumber) { // digits ended, check if part number
          print("$curDigitString  ")
          if (hasSymbolAdjacent(lineNumber,curIdx - curDigitString.length, curIdx - 1)) {
            partNumbers.add(curDigitString)
          }

          isScanningNumber = false
          curDigitString = ""
        }
      }
    }
    if (isScanningNumber) { // digits ended, check if part number
      print("$curDigitString  ")
      if (hasSymbolAdjacent(lineNumber, line.size - curDigitString.length, line.size - 1)) {
        partNumbers.add(curDigitString)
      }
    }

      return partNumbers.map { it.toInt() }
  }

  fun hasSymbolAdjacent(line: Int, beginIndex: Int, endIndex: Int) : Boolean {
    for (x in beginIndex..endIndex)
      if (hasSymbolAdjacent(line, x))
        return true

    return false
  }

  private fun hasSymbolAdjacent(line: Int, index: Int) : Boolean {
    for (x in -1..1 )
      for (y in -1..1 ) {
       if (isSymbol(line +y, index+x))
         return true
      }

    return false
  }

  private fun isSymbol(lineNumber: Int, index: Int): Boolean {
    if (lineNumber < 0 || index < 0 || lines.size <= lineNumber || lines[lineNumber].length <= index)
      return false

    val char = lines[lineNumber][index]

    return !(char.isDigit() || char == '.')
  }

  private fun findDigit(lineNumber: Int, index: Int): Char? {
    if (lineNumber < 0 || index < 0 || lines.size <= lineNumber || lines[lineNumber].length <= index)
      return null

    val char = lines[lineNumber][index]

    return if (char.isDigit()) char else null
  }
}

class Day3