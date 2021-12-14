class Day14(filename: String) {
	private lateinit var template: String
	private val rules = mutableMapOf<String, Char>()

	init {
		readFileAndProcess(filename) { line ->
			when {
				line.isEmpty() -> Unit
				line.contains("->") -> {
					val (pair, insert) = line.split(" -> ")
					rules[pair] = insert[0]
				}
				else -> template = line
			}
		}
	}

	fun getElementalDifference(steps: Int = 10): Long {
		var pairCounts =
			template
				.zipWithNext { a, b -> a.toString() + b }
				.groupingBy { it }
				.eachCount()
				.mapValues { (_, v) -> v.toLong() }

		val charCounts = template
			.groupingBy { it }
			.eachCount()
			.mapValues { (_, v) -> v.toLong() }
			.toMutableMap()

		for (step in 1..steps) {
			pairCounts = applyRules(pairCounts, charCounts)
		}

		return charCounts.maxOf { (_, v) -> v } - charCounts.minOf { (_, v) -> v }
	}

	private fun applyRules(
		pairCounts: Map<String, Long>,
		charCounts: MutableMap<Char, Long>
	): Map<String, Long> {
		val newPairCounts = mutableMapOf<String, Long>().withDefault { 0 }

		for ((pair, count) in pairCounts) {
			newPairCounts[pair] = newPairCounts(pair) + count
			if (pair in rules) {
				newPairCounts[pair] = newPairCounts(pair) - count

				val char = rules(pair)
				val firstPair = pair[0].toString() + char
				newPairCounts[firstPair] = newPairCounts(firstPair) + count
				val secondPair = char.toString() + pair[1]
				newPairCounts[secondPair] = newPairCounts(secondPair) + count

				charCounts.merge(char, count) { old, c -> old + c }
			}
		}
		return newPairCounts
	}
}