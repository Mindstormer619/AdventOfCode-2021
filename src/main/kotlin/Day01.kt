class Day01(filename: String) {
	private val depths: List<Int>
	init {
		depths = readFileAndProcess(filename) {it.toInt()}
	}

	/**
	 * Solution for Part 1
	 */
	fun getNumberOfDepthIncreases(): Int {
		var currentCount = 0
		for (i in depths.indices) {
			if (i == depths.lastIndex) break
			if (depths[i+1] > depths[i]) currentCount++
		}
		return currentCount
	}

	/**
	 * Solution for Part 2
	 */
	fun getSlidingWindowDepthIncreases(): Int {
		var currentCount = 0
		for (i in depths.indices) {
			// comparing (i, i+1, i+2) with (i+1, i+2, i+3)
			if (i+3 > depths.lastIndex) break
			val windowA = depths.slice(i..i+2).sum()
			val windowB = depths.slice(i+1..i+3).sum()

			if (windowA < windowB) currentCount++
		}
		return currentCount
	}
}