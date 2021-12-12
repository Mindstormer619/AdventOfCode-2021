class Day12(filename: String) {
	private val network: MutableMap<Cave, MutableSet<Cave>> = mutableMapOf()
	init {
		readFileAndProcess(filename) {
			val (cave1, cave2) =  it.split('-')
			if (network[cave1] == null) network[cave1] = mutableSetOf(cave2)
			else network[cave1]?.add(cave2)

			if (network[cave2] == null) network[cave2] = mutableSetOf(cave1)
			else network[cave2]?.add(cave1)
		}
	}

	fun getNumberOfPaths(): Int {
		return CaveNavigator(network).navigateCaves("start")
	}

	fun getPathsAllowingTwoVisits(): Int {
		return CaveNavigator(network, true).navigateCaves("start")
	}

	private class CaveNavigator(
		val network: Map<Cave, Set<Cave>>,
		val allowTwo: Boolean = false
	) {
		private val smallCavesVisited = network
			.mapValues { 0 }
			.filter { it.key != "end" && it.key.isSmallCave }
			.toMutableMap()

		fun navigateCaves(c: Cave): Int {
			if (c == "end") return 1
			if (c.isSmallCave && !visitingIsAllowed(c)) return 0

			visit(c)
			var totalPaths = 0
			for (neighbor in network[c]!!) {
				if (neighbor == "start") continue
				totalPaths += navigateCaves(neighbor)
			}
			unvisit(c)
			return totalPaths
		}

		private fun visit(c: Cave) {
			if (c.isSmallCave) smallCavesVisited[c] = smallCavesVisited[c]!! + 1
		}

		private fun unvisit(c: Cave) {
			if (c.isSmallCave) smallCavesVisited[c] = smallCavesVisited[c]!! - 1
		}

		private fun visitingIsAllowed(c: Cave): Boolean {
			val mostVisits = smallCavesVisited.maxOf { it.value }
			if (allowTwo && mostVisits < 2) return true
			return smallCavesVisited[c]!! == 0
		}

		private val Cave.isSmallCave: Boolean
			get() = this != "start" && this.lowercase() == this
	}
}

typealias Cave = String
