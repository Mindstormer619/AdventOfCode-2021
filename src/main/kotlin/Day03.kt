import kotlin.text.StringBuilder

class Day03(filename: String) {
	private val input: List<String>
	init {
		input = readFileAndProcess(filename) { it }
	}

	fun getPowerConsumption(): Long {
		val countOf1 = Array(input[0].length) {0}
		for (num in input) {
			for (i in num.indices) {
				if (num[i] == '1') countOf1[i]++
			}
		}
		val gamma = StringBuilder()
		val epsilon = StringBuilder()

		for (c in countOf1) {
			if (c > input.size / 2) { // 1 is the most common bit at this position
				gamma.append('1')
				epsilon.append('0')
			}
			else {
				gamma.append('0')
				epsilon.append('1')
			}
		}

		return gamma.toString().toLong(2) * epsilon.toString().toLong(2)
	}

	fun getLifeSupportRating(): Long {
		val numLength = input[0].length

		var oxygen = input
		for (i in 0 until numLength) {
			val bit = oxygen.findMostCommonBitAt(i)
			oxygen = oxygen.filter { it[i] == bit }
			if (oxygen.size == 1) break
		}

		var co2 = input
		for (i in 0 until numLength) {
			val bit = if (co2.findMostCommonBitAt(i) == '1') '0' else '1'
			co2 = co2.filter { it[i] == bit }
			if (co2.size == 1) break
		}

		return oxygen.first().toLong(2) * co2.first().toLong(2)
	}


}

private fun List<String>.findMostCommonBitAt(i: Int) =
	if (this.count { it[i] == '1' } >= this.size / 2.0) '1' else '0'
