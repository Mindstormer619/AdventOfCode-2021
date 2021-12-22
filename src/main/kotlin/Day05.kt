import kotlin.math.abs

class Day05(filename: String) {
	private val segments: List<Pair<Point, Point>>

	init {
		segments = readFileAndProcess(filename) { line ->
			val (a, b, _) = line.split(" -> ")
			val (a1, a2) = a.split(',').map { it.toInt() }
			val (b1, b2) = b.split(',').map { it.toInt() }
			Point(a1, a2) to Point(b1, b2)
		}
	}

	data class Point(val x: Int, val y: Int)

	fun getDangerousPoints(): Int {
		val straightSegments = segments.filter {
			(it.first.x == it.second.x) || (it.first.y == it.second.y)
		}

		return countDangerousPoints(straightSegments)
	}

	fun getAllDangerousPoints(): Int {
		val straightOr45Segments = segments.filter {
			(it.first.x == it.second.x)
			|| (it.first.y == it.second.y)
			|| (abs(it.first.x - it.second.x) == abs(it.first.y - it.second.y))
		}

		return countDangerousPoints(straightOr45Segments)
	}

	private fun countDangerousPoints(segments: List<Pair<Point, Point>>): Int {
		val pointMap = mutableMapOf<Point, Int>()

		for (segment in segments) {
			val (start, end) = segment
			if (start.x == end.x) {
				for (y in start.y towards end.y) {
					pointMap.merge(Point(start.x, y), 1) { existing, _ -> existing + 1 }
				}
			}
			else if (start.y == end.y) {
				for (x in start.x towards end.x) {
					pointMap.merge(Point(x, start.y), 1) { existing, _ -> existing + 1 }
				}
			}
			else {
				for ((x,y) in (start.x towards end.x) zip (start.y towards end.y)) {
					pointMap.merge(Point(x, y), 1) { existing, _ -> existing + 1 }
				}
			}
		}

		return pointMap.count { entry -> entry.value >= 2 }
	}


}