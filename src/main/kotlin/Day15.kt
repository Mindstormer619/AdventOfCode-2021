class Day15(filename: String) {
	private val riskLevels: List<List<Int>>

	init {
		riskLevels = readFileAndProcess(filename) { line ->
			line.map { it.digitToInt() }
		}
	}

	fun getLowestRisk(): Int {
		setOf<Pair<Int, Int>>()
		return Navigator(riskLevels).navigate(0, 0)
	}

	private class Navigator(val riskLevels: List<List<Int>>) {
		val optimalRisk = mutableMapOf<Pair<Int, Int>, Int>()

		fun navigate(r: Int, c: Int, visited: Set<Pair<Int, Int>> = setOf()): Int {
			val currValue = riskLevels.check(r, c) ?: return Int.MAX_VALUE
			if ((r to c) in visited) return Int.MAX_VALUE
			if (r == riskLevels.lastIndex && c == riskLevels[r].lastIndex)
				return currValue
//			if (optimalRisk[r to c] != null) return optimalRisk(r to c)

			val newVisited = visited + (r to c)
			val paths = listOf(
				navigate(r + 1, c, newVisited) to "dn",
				navigate(r, c + 1, newVisited) to "rt",
				navigate(r - 1, c, newVisited) to "up",
				navigate(r, c - 1, newVisited) to "lt"
			)
			val (minPath, dir) = paths.minByOrNull { it.first }!!
			val minRisk = if (minPath == Int.MAX_VALUE) Int.MAX_VALUE else currValue + minPath
			optimalRisk[r to c] = minRisk
			return minRisk
		}
	}


}
