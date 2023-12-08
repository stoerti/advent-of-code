package io.github.stoerti.aoc

class IOUtils {
  companion object {
    fun readInput(resource: String): List<String> {
      return object {}.javaClass.getResourceAsStream("../../../../${resource}.txt")!!
        .bufferedReader()
        .readLines()
    }
  }
}

class MathUtils {
  companion object {
    fun getLeastCommonMultiple(numbers: Collection<Long>): Long {
      return numbers.map { getPrimes(it) }.reduce { i, j -> i + j }.reduce { i, j -> i * j }
    }

    fun getPrimes(number: Long): Set<Long> {
      val primes = mutableSetOf<Long>()

      var currentNumber = number
      var i = 2L
      while (i * i < currentNumber) {
        if (number % i == 0L) {
          primes.add(i)
          currentNumber /= i
        }
        i++
      }

      primes.add(currentNumber)
      return primes
    }
  }
}

object StringExt {
  fun String.nonEmptyLines() = lines().filterNot(String::isEmpty)

  fun String.chunkedByEmpty(): List<List<String>> = this.split("\n\n").map { it.nonEmptyLines() }

  fun String.toPair(splitter: String): Pair<String, String> {
    val (a, b) = this.split(splitter)
    return a.trim() to b.trim()
  }

  fun String.splitTrimmed(splitter: String): List<String> =
    this.split(splitter).map { it.trim() }.filterNot(String::isEmpty)

  fun String.intValues() = this.split("\\D".toRegex()).map { it.trim() }.filterNot { it.isBlank() }.map { it.toInt() }
  fun String.longValues() = this.split("\\D".toRegex()).map { it.trim() }.filterNot { it.isBlank() }.map { it.toLong() }
}
