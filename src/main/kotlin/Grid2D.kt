package io.github.stoerti.aoc

class Grid2D<T>(
  val map: List<List<T>>,
) {
  companion object {
    fun charGrid(lines: List<String>): Grid2D<Char> =
      Grid2D(lines.map { it.toCharArray().toList() })
  }

  fun get(coordinate: Coordinate): T? =
    if (valid(coordinate)) map[coordinate.y][coordinate.x]
    else null;

  fun valid(coordinate: Coordinate): Boolean =
    (coordinate.y >= 0 && map.size > coordinate.y && coordinate.x >= 0 && map[coordinate.y].size > coordinate.x)

  data class Coordinate(val x: Int, val y: Int) {
    fun up() = Coordinate(x, y-1)
    fun down() = Coordinate(x, y+1)
    fun left() = Coordinate(x - 1, y)
    fun right() = Coordinate(x + 1, y)

    override fun toString(): String = "[$x, $y]"
  }

  override fun toString(): String {
    return map.toString()
  }
}

/*
[7, -, F, 7, -],
[., F, J, |, 7],
[S, J, L, L, 7],
[|, F, -, -, J],
[L, J, ., L, J]
*/