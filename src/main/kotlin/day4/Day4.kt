package day4

import kotlin.math.pow

fun main(args: Array<String>) {
  val cards = object {}.javaClass.getResourceAsStream("../day_4_input.txt")!!
    .bufferedReader()
    .readLines()
    .map { Card.fromString(it) }
    .onEach { println(it) }

  val result1 = cards
    .map { it.getMyWinningNumbers() }
    .map { if (it.isEmpty()) 0 else       2.0.pow(it.size-1).toInt() }
    .sumOf { it }

  val numCards = cards.associate { it.cardNumber to 1 }.toMutableMap()
  cards.forEach { card ->
    for (i in 1..card.getNumWinners()) {
      numCards[card.cardNumber+i] = numCards[card.cardNumber+i]!! + 1 * numCards[card.cardNumber]!!
    }
  }

  val result2 = numCards.values.sum()

  println("Result 1: $result1")
  println("Result 2: $result2")
}

data class Card(
  val cardNumber: Int,
  val winningNumbers: List<Int>,
  val myNumbers: List<Int>,
) {
  companion object {
    fun fromString(line: String) : Card {
      val cardNumber = line.split(":")[0].substring(5).trim().toInt()
      val winningNumbers = line.split(":")[1].split("|")[0].split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() }
      val myNumbers = line.split(":")[1].split("|")[1].split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() }

      return Card(cardNumber,winningNumbers, myNumbers)
    }
  }

  fun getMyWinningNumbers(): List<Int> {
    return myNumbers.filter { winningNumbers.contains(it) }
  }

  fun getNumWinners(): Int {
    return getMyWinningNumbers().size
  }
}


class Day4