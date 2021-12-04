import java.io.File

class Day04(filename: String) {
	private val boards = mutableListOf<Board>()
	private val callouts: List<Int>

	init {
		val lines = File(filename).readLines()

		callouts = lines.first().split(',').map { it.toInt() }

		val spaces = Regex(" +")
		for (line in lines.subList(1, lines.size)) {
			when {
				line.isBlank() -> boards.add(Board())
				else -> {
					boards.last().addRow(line.trim().split(spaces).map { it.toInt() })
				}
			}
		}
	}

	fun getBingoScore(): Long {
		for (callout in callouts) {
			for (board in boards) {
				val hasBingo = board.checkOff(callout)
				if (hasBingo) {
					val unmarkedSum = board.getUnmarkedSum()
					return unmarkedSum * callout.toLong()
				}
			}
		}
		return 0
	}

	fun getLastBoardScore(): Long {
		val bingoBoards = mutableSetOf<Board>()

		for (callout in callouts) {
			for (board in boards) {
				if (board in bingoBoards) continue
				val hasBingo = board.checkOff(callout)
				if (hasBingo) {
					bingoBoards.add(board)
					if (bingoBoards.size == boards.size) {
						val unmarkedSum = board.getUnmarkedSum()
						return unmarkedSum * callout.toLong()
					}
				}
			}
		}
		return 0
	}

	class Square(val value: Int, var checked: Boolean = false)

	class Board {
		private val _rows = mutableListOf<List<Square>>()
		val rows: List<List<Square>> = _rows


		fun addRow(values: List<Int>) {
			val row = values.map { Square(it, false) }
			_rows.add(row)
		}

		fun checkOff(calledNum: Int): Boolean {
			for (r in rows.indices) {
				for (s in rows[r].indices) {
					val square = rows[r][s]
					if (square.value == calledNum) {
						square.checked = true
						return checkBingo(r, s)
					}
				}
			}
			return false
		}

		private fun checkBingo(r: Int, s: Int): Boolean {
			var bingoFound = true
			// checking row
			for (square in rows[r]) {
				if (!square.checked) {
					bingoFound = false
					break
				}
			}
			if (bingoFound) return true
			bingoFound = true
			// checking column
			for (row in rows) {
				if (!row[s].checked) {
					bingoFound = false
					break
				}
			}
			return bingoFound
		}

		fun getUnmarkedSum(): Int {
			val unmarkedSum = this.rows.sumOf { row ->
				row.sumOf { square ->
					if (square.checked) 0 else square.value
				}
			}
			return unmarkedSum
		}
	}
}