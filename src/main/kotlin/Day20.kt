import kotlin.properties.Delegates

class Day20(filename: String) {
	var activePoints = mutableSetOf<Pair<Int, Int>>()
	private lateinit var enhancer: String
	var isInfinityLit = false

	var rowRange: IntRange
	lateinit var colRange: IntRange

	init {
		var r = 0
		var haveReadEnhancer = false
		readFileAndProcess(filename) { line ->
			when {
				line.isBlank() -> Unit
				!haveReadEnhancer -> {
					enhancer = line
					haveReadEnhancer = true
				}
				else -> {
					activePoints += line.withIndex()
						.filter { it.value == '#' }
						.map { r to it.index }
					colRange = 0..line.lastIndex
					r++
				}
			}
		}
		rowRange = 0 until r
	}

	fun getNumberOfPixels(passes: Int): Int {
		for (pass in 1..passes) {
			runImageProcess()
		}
		return activePoints.size
	}

	private fun runImageProcess() {
		val newPoints = mutableSetOf<Pair<Int, Int>>()
		for (y in rowRange.first-1 .. rowRange.last+1) {
			for (x in colRange.first - 1..colRange.last + 1) {
				val point = x to y
				if (enhancer[getEnhancerIndex(point)] == '#') newPoints += point
			}
		}
		activePoints = newPoints
		// extend the boundary
		rowRange = rowRange.first-1 .. rowRange.last+1
		colRange = colRange.first - 1..colRange.last + 1
		// update infinity state
		isInfinityLit = if (isInfinityLit) {
			enhancer.last() == '#'
		} else {
			enhancer.first() == '#'
		}
	}

	fun getEnhancerIndex(point: Pair<Int, Int>): Int {
		val neighbors = generateNeighbors(point)
		return neighbors
			.map { if (isPointActive(it)) '1' else '0' }
			.joinToString("")
			.toInt(2)
	}

	private fun isPointActive(point: Pair<Int, Int>): Boolean {
		return if (
			point.first in rowRange
			&& point.second in colRange
		) point in activePoints
		else isInfinityLit
	}

	private fun generateNeighbors(point: Pair<Int, Int>): List<Pair<Int, Int>> {
		val neighbors = mutableListOf<Pair<Int, Int>>()
		for (x in point.first-1..point.first+1) {
			for (y in point.second-1..point.second+1) {
				neighbors += x to y
			}
		}
		return neighbors
	}
}

