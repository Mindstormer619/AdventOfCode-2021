import kotlin.math.min

class Day13(filename: String) {
	private val points = mutableSetOf<Point>()
	private val folds = mutableListOf<Fold>()

	init {
		readFileAndProcess(filename) { line ->
			when {
				line.isEmpty() -> Unit
				line.startsWith("fold") -> {
					folds.add(Fold(line))
				}
				else -> {
					val split = line.split(',')
					points.add(split[0].toInt() to split[1].toInt())
				}
			}
		}
	}

	fun getDots(numFolds: Int): Int {
		return runFolds(numFolds).size
	}

	fun printDots() {
		val finalDots = runFolds(folds.size)
		val maxX = finalDots.maxOf { it.first }
		val maxY = finalDots.maxOf { it.second }

		for (y in 0..maxY) {
			for (x in 0..maxX) {
				if ((x to y) in finalDots) {
					print('#')
				}
				else print(' ')
			}
			println()
		}
	}

	private fun runFolds(numFolds: Int): Set<Point> {
		var currentPoints = points.toSet()
		for (i in 0 until numFolds) {
			val fold = folds[i]
			val newSet = mutableSetOf<Point>()
			for (point in currentPoints) {
				newSet += fold.reflect(point)
			}
			currentPoints = newSet
		}
		return currentPoints
	}

	data class Fold(val isX: Boolean, val axis: Int) {
		companion object {
			private val regex = Regex("fold along (.)=(.*)")
			operator fun invoke(input: String): Fold {
				val groups = regex.matchEntire(input)!!.groupValues
				return if (groups[1] == "x") Fold(true, groups[2].toInt())
				else Fold(false, groups[2].toInt())
			}
		}

		fun reflect(point: Point): Point {
			return if (isX) {
				min(point.first, 2 * axis - point.first) to point.second
			} else {
				point.first to min(point.second, 2 * axis - point.second)
			}
		}
	}
}

private typealias Point = Pair<Int, Int>