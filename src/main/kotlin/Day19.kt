class Day19(filename: String) {
	val data = mutableListOf<Set<Vector>>()
	init {
		var vectors = mutableSetOf<Vector>()
		readFileAndProcess(filename) {
			if (it.isEmpty()) data += vectors
			else if (it.startsWith("---")) vectors = mutableSetOf()
			else {
				val vector = Vector(it)
				vectors += vector
			}
		}
	}

	data class Vector(val coordinates: List<Int>) {
		constructor(init: String) : this(init.split(',').map { it.toInt() })
	}

	fun countBeacons(): Int {
		TODO("Not yet implemented")
	}
}