class Day08(filename: String) {

	private val data: List<Pair<Display, Display>>

	init {
		data = readFileAndProcess(filename) { line ->
			val (input, output) = line
				.split('|')
				.map { it.trim().split(' ') }
				.map { digits -> digits.map { it.toCharArray().sorted().joinToString("") } }
			input to output
		}
	}

	fun get1478Count(): Int =
		data.sumOf {
			it.second.count { digit ->
				when (digit.length) {
					2, 4, 3, 7 -> true
					else -> false
				}
			}
		}

	fun getTotalOutput(): Int {
		var total = 0
		for ((input, output) in data) {
			val digits = analyzeInput(input)
			val value = output.map { digits.indexOf(it) }.reduce { value, nextDigit -> value * 10 + nextDigit }
			total += value
		}
		return total
	}

	private fun analyzeInput(input: List<String>): Array<String> {
		val segments = Segments()
		val digits = Array(10) { digit ->
			when(digit) {
				1 -> input.first { it.length == 2 }
				4 -> input.first { it.length == 4 }
				7 -> input.first { it.length == 3 }
				8 -> input.first { it.length == 7 }
				else -> ""
			}
		}

		val set069 = input.filter { it.length == 6 }.toSet()
		val set532 = input.filter { it.length == 5 }.toSet()

		// top segment
		segments.top = (digits[7].toSet() - digits[1].toSet()).first()

		// digit 6
		digits[6] = set069.first {
			!it.toSet().containsAll(digits[7].toSet())
		}

		segments.topright = (digits[7].toSet() - digits[6].toSet()).first()
		segments.bottomright = (digits[7].toSet() - setOf(segments.top, segments.topright)).first()

		// 9 and 0
		digits[9] = set069.first {
			it.toSet().containsAll(digits[4].toSet())
		}
		digits[0] = (set069 - setOf(digits[6], digits[9])).first()

		// 2 3 5
		digits[2] = set532.first {it.toSet().contains(segments.topright) && !it.toSet().contains(segments.bottomright)}
		digits[3] = set532.first {it.toSet().contains(segments.topright) && it.toSet().contains(segments.bottomright)}
		digits[5] = set532.first {!it.toSet().contains(segments.topright) && it.toSet().contains(segments.bottomright)}

		return digits
	}

	private data class Segments(
		var top: Char,
		var topleft: Char,
		var topright: Char,
		var bottomleft: Char,
		var bottomright: Char,
		var center: Char,
		var bottom: Char
	) {
		constructor(): this(' ', ' ', ' ', ' ', ' ', ' ', ' ')
	}
}

private typealias Display = List<String>