import kotlin.math.abs

class Day07(filename: String) {
	private val initPositions: List<Int>

	init {
		initPositions = readFileAndProcess(filename) { line ->
			line.split(',').map { it.toInt() }
		}.first()
	}

	fun getMinimumFuelUsed(formula: (Int, Int) -> Int = FuelUse::linearFuelUseFormula): Int {
		val smallestPositionPossible = initPositions.minOrNull()!!
		val largestPositionPossible = initPositions.maxOrNull()!!

		return (smallestPositionPossible..largestPositionPossible).minOf { position ->
			initPositions.sumOf{formula(it, position)}
		}
	}

	object FuelUse {
		fun linearFuelUseFormula(sourcePosition: Int, targetPosition: Int) =
			abs(sourcePosition - targetPosition)

		fun nonlinearFuelUseFormula(sourcePosition: Int, targetPosition: Int): Int {
			val n = abs(sourcePosition - targetPosition)
			return n * (n+1) / 2
		}
	}
}