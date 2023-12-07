package io.github.stoerti.aoc.day7

import io.github.stoerti.aoc.IOUtils
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main(args: Array<String>) {
  val lines = IOUtils.readInput("day_7_input")

  val result1 = lines.map { it.split(" ") }
    .map { Hand(it[1].toInt(), it[0]) }
    .sortedBy { it.getHandValue() }
//  .onEachIndexed { i, it -> println("${i + 1} ${it.cards} ${it.bet} ${it.getHandValue()}") }
    .mapIndexed { i, it -> (i + 1) * it.bet }
    .sum()

  val result2 = lines.map { it.split(" ") }
    .map { Hand(it[1].toInt(), it[0], true) }
    .sortedBy { it.getHandValue() }
//  .onEachIndexed { i, it -> println("${i + 1} ${it.cards} ${it.bet} ${it.getHandValue()}") }
    .mapIndexed { i, it -> (i + 1) * it.bet }
    .sum()

  println("Result1: $result1")
  println("Result2: $result2")
}

data class Hand(
  val bet: Int,
  val cards: String,
  val jokerRule: Boolean = false
) {

  private val groupedCards = cards.toCharArray().filter { !jokerRule || it != 'J' } .groupBy { it }.mapValues { it.value.size }
  val numJokers = cards.toCharArray().count { jokerRule && it == 'J' }

  private fun hasFiveOfAKind(): Boolean = numJokers == 5 || groupedCards.values.maxOf { it + numJokers } == 5
  private fun hasFourOfAKind(): Boolean = groupedCards.values.maxOf { it + numJokers } == 4
  private fun hasFullHouse(): Boolean = ( groupedCards.containsValue(3) && groupedCards.containsValue(2)) ||
          ( groupedCards.filterValues { it == 2 }.size == 2 && numJokers == 1)

  private fun hasThreeOfAKind(): Boolean = groupedCards.values.maxOf { it + numJokers } == 3

  private fun hasTwoPair(): Boolean = groupedCards.filterValues { it == 2 }.size == 2
  private fun hasOnePair(): Boolean = groupedCards.values.maxOf { it + numJokers } == 2

  private fun getHandType(): String {
    if (hasFiveOfAKind()) return "9"
    if (hasFourOfAKind()) return "8"
    if (hasFullHouse()) return "7"
    if (hasThreeOfAKind()) return "6"
    if (hasTwoPair()) return "5"
    if (hasOnePair()) return "4"
    return "0"
  }

  fun getHandValue(): String {
    return this.getHandType() +
            mapCardToValue(cards[0]) +
            mapCardToValue(cards[1]) +
            mapCardToValue(cards[2]) +
            mapCardToValue(cards[3]) +
            mapCardToValue(cards[4])
  }

  fun mapCardToValue(card: Char): String {
    return when (card) {
      'A' -> "14"
      'K' -> "13"
      'Q' -> "12"
      'J' -> "01"
      'T' -> "10"
      else -> "0$card"
    }
  }
}

