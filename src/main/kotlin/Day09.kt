class Day09(filename: String) {
	private val grid: List<List<Int>>

	init {
		grid = readFileAndProcess(filename) { line ->
			line.map { it.digitToInt() }
		}
	}

	fun getTotalRiskLevel(): Int {
		var totalRisk = 0
		for (r in grid.indices) {
			for (c in grid[r].indices) {
				val cell = grid[r][c]
				if (
					cell < gridCheck(r - 1, c)
					&& cell < gridCheck(r + 1, c)
					&& cell < gridCheck(r, c - 1)
					&& cell < gridCheck(r, c + 1)
				) totalRisk += (cell + 1)
			}
		}
		return totalRisk
	}

	private fun gridCheck(r: Int, c: Int): Int =
		if (r !in grid.indices || c !in grid[r].indices) 10
		else grid[r][c]

	fun getBasinProduct(): Long {
		val visited = grid.map { row -> row.map { false }.toMutableList() }
		val topThreeBasins = mutableListOf(0, 0, 0)

		for (r in grid.indices) {
			for (c in grid[r].indices) {
				if (visited[r][c]) continue
				val basinSize = getBasinSize(r, c, visited)
				if (basinSize > topThreeBasins.minOrNull()!!) {
					topThreeBasins.remove(topThreeBasins.minOrNull()!!)
					topThreeBasins.add(basinSize)
				}
			}
		}
		return topThreeBasins.fold(1L) {p, e -> p * e}
	}

	private fun getBasinSize(r: Int, c: Int, visited: List<MutableList<Boolean>>): Int {
		if (gridCheck(r, c) >= 9 || visited[r][c]) return 0
		visited[r][c] = true
		return (
			1
			+ getBasinSize(r-1, c, visited)
			+ getBasinSize(r+1, c, visited)
			+ getBasinSize(r, c-1, visited)
			+ getBasinSize(r, c+1, visited)
		)
	}


}
