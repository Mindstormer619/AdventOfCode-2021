class Day11(filename: String) {
	private val grid: List<MutableList<Int>>

	init {
		grid = readFileAndProcess(filename) { line ->
			line.map { it.digitToInt() }.toMutableList()
		}
	}

	fun simulateFlashes(steps: Int): Int {
		var totalFlashes = 0

		for (step in 1..steps) {
			totalFlashes += runFlashIteration()
		}

		return totalFlashes
	}

	fun fullFlash(steps: Int): Int {
		for (step in 1..steps) {
			val flashCount = runFlashIteration()
			if (flashCount == grid.size * grid[0].size) return step
		}

		return 0
	}

	private fun runFlashIteration(): Int {
		val flashed = grid.map { row -> row.map { false }.toMutableList() }

		for (r in grid.indices) {
			for (c in grid[r].indices) {
				grid[r][c] += 1
				flash(r, c, flashed)
			}
		}

		resetFlashed()
		return flashed.sumOf { row -> row.count { it } }
	}

	private fun resetFlashed() {
		for (r in grid.indices) {
			for (c in grid[r].indices) {
				if (grid[r][c] > 9) grid[r][c] = 0
			}
		}
	}

	private fun flash(r: Int, c: Int, flashed: List<MutableList<Boolean>>) {
		val octopus = gridCheck(r, c)
		if (octopus == null || flashed[r][c]) return

		if (octopus > 9) {
			flashed[r][c] = true
			for (_r in r - 1..r + 1)
				for (_c in c - 1..c + 1)
					if (gridCheck(_r, _c) != null) grid[_r][_c] += 1
			for (_r in r - 1..r + 1)
				for (_c in c - 1..c + 1)
					flash(_r, _c, flashed)
		}
	}

	private fun gridCheck(r: Int, c: Int): Int? =
		if (r !in grid.indices || c !in grid[r].indices) null
		else grid[r][c]

}




