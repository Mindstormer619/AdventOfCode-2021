import kotlin.math.abs

class Day17(targetString: String) {
	private val targetArea: Pair<IntRange, IntRange>

	init {
		val regex = Regex("""target area: x=(.*)\.\.(.*), y=(.*)\.\.(.*)""")
		val g = regex.matchEntire(targetString)!!.groupValues
		targetArea = g[1].toInt()..g[2].toInt() to g[3].toInt()..g[4].toInt()
	}

	fun getMaxY(): Int {
		val velocities = getVelocities().map { it.second }
		var yMax = 0
		for (uy in velocities) {
			var t = 0
			var y = 0
			var vy = uy
			while (vy > 0) {
				t += 1
				y += vy
				vy -= 1
			}
			if (y > yMax) yMax = y
		}
		return yMax
	}

	fun getVelocities(): MutableList<Pair<Int, Int>> {
		val yVelocities = findYVelocity()
		val xVelocities = findXVelocity(yVelocities.keys)

		val velocities = mutableListOf<Pair<Int, Int>>()
		for (t in yVelocities.keys) {
			if (xVelocities.containsKey(t)) {
				for (vx in xVelocities(t))
					for (vy in yVelocities(t))
						velocities += vx to vy
			}
		}
		return velocities
	}

	fun findYVelocity(): MutableMap<Int, List<Int>> {
		val yMin = targetArea.second.first

		val yVelocities = mutableMapOf<Int, List<Int>>()
		velocity@for (uy in -abs(yMin)..abs(yMin)) {
			var t = 0
			var y = 0
			var vy = uy
			while (y >= yMin) {
				t += 1
				y += vy
				vy -= 1
				if (y in targetArea.second) {
					yVelocities.merge(t, listOf(uy)) {old, new -> old+new}
				}
			}
		}

		return yVelocities
	}

	fun findXVelocity(times: Set<Int>): MutableMap<Int, List<Int>> {
		val xMax = targetArea.first.last

		val xVelocities = mutableMapOf<Int, List<Int>>()
		for (ux in 1..xMax) {
			val xValues = times
				.map { t ->
					t to
						if (t < ux) (t * ux) - ((t * (t - 1)) / 2)
						else (ux * ux) - ((ux * (ux - 1)) / 2)
				}
				.filter { (_, x) -> x in targetArea.first }
			for ((t, _) in xValues) {
				xVelocities.merge(t, listOf(ux)) {old, new -> old+new}
			}
		}

		return xVelocities
	}
}