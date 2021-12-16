import java.util.*

class Day15(filename: String) {
	private val riskLevels: List<List<Int>>

	init {
		riskLevels = readFileAndProcess(filename) { line ->
			line.map { it.digitToInt() }
		}
	}

	fun getLowestRisk(): Int {
		return Navigator(riskLevels).navigate()
	}

	fun getScaledRisk(): Int {
		val risks = mutableListOf<MutableList<Int>>()

		for (vert in 0 until 5) {
			for (row in riskLevels) {
				val localRow = mutableListOf<Int>()
				localRow.addAll(row.map {
					val i = it + vert
					((i - 1) mod 9) + 1
				})
				for (horz in 0 until 4) {
					for (i in row.indices) {
						val v = localRow[horz * row.size + i]
						localRow.add((v mod 9) + 1)
					}
				}
				risks.add(localRow)
			}
		}
		return Navigator(risks).navigate()
	}

	private class Navigator(val riskLevels: List<List<Int>>) {
		val optimalRisk = PriorityQueue<Node>()
		val riskMap = mutableMapOf<Vertex, Int>().withDefault { Int.MAX_VALUE }
		val visitedVertices = mutableSetOf<Vertex>()

		data class Node(val vertex: Vertex, val risk: Int): Comparable<Node> {
			override fun compareTo(other: Node) = this.risk - other.risk
		}

		init {
			riskMap[0 to 0] = 0
			optimalRisk += Node(0 to 0, 0)
		}

		fun navigate(): Int {
			while (optimalRisk.isNotEmpty()) {
				val (vertex, risk) = optimalRisk.poll()
				visitedVertices += vertex

				if (vertex == riskLevels.lastIndex to riskLevels[0].lastIndex) return risk

				for (neighbor in vertex.neighbors()) {
					if (
						riskLevels.check(neighbor.first, neighbor.second) == null
						|| neighbor in visitedVertices
					) continue

					val updatedRisk = risk + riskLevels[neighbor.first][neighbor.second]
					if (updatedRisk < riskMap(neighbor)) {
						riskMap[neighbor] = updatedRisk
						optimalRisk += Node(neighbor, updatedRisk)
					}
				}
			}
			return riskMap[riskLevels.lastIndex to riskLevels[0].lastIndex]!!
		}

		private fun Vertex.neighbors(): List<Vertex> {
			return listOf(
				first - 1 to second,
				first + 1 to second,
				first to second + 1,
				first to second - 1
			)
		}
	}
}

private typealias Vertex = Pair<Int, Int>
