class Day02(filename: String) {
	private val directions: List<Move>

	init {
		directions = readFileAndProcess(filename) {
			val (axis, magnitude, _) = it.split(' ')
			when (axis) {
				"forward" -> Move(true, magnitude.toLong())
				"down" -> Move(false, magnitude.toLong())
				"up" -> Move(false, magnitude.toLong() * -1)
				else -> throw NotImplementedError()
			}
		}
	}

	data class Move(val isHorizontal: Boolean, val magnitude: Long)

	fun getFinalPositionNumber(): Long {
		var xPosition: Long = 0
		var yPosition: Long = 0

		for (d in directions) {
			if (d.isHorizontal) {
				xPosition += d.magnitude
			}
			else {
				yPosition += d.magnitude
			}
		}
		return xPosition * yPosition
	}

	fun getAimPositionNumber(): Long {
		var xPosition = 0L
		var yPosition = 0L
		var aim = 0L

		for (d in directions) {
			if (d.isHorizontal) {
				xPosition += d.magnitude
				yPosition += aim * d.magnitude
			}
			else {
				aim += d.magnitude
			}
		}

		return xPosition * yPosition
	}
}
