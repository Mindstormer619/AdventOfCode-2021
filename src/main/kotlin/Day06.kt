class Day06(filename: String) {
	private val initFish: List<Long>
	init {
		initFish = readFileAndProcess(filename) { line ->
			line.split(',').map { it.toLong() }
		}.first()
	}

	fun getNumberOfFish(days: Int): Long {
		var fishStages = Array(9) { stage -> initFish.count {it == stage.toLong()}.toLong() }

		for (day in 1..days) {
			val nextDayStages = Array(9) {0L}
			for (i in nextDayStages.indices) {
				nextDayStages[i] = when(i) {
					8 -> fishStages[0]
					6 -> fishStages[7] + fishStages[0]
					else -> fishStages[i+1]
				}
			}
			fishStages = nextDayStages
		}
		return fishStages.sum()
	}
}