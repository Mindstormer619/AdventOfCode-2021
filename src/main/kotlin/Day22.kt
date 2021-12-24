import kotlin.math.max
import kotlin.math.min

class Day22(filename: String) {
	private val instructions: List<Instruction>
	private val regex = Regex("""(.*) x=(.*)\.\.(.*),y=(.*)\.\.(.*),z=(.*)\.\.(.*)""")

	init {
		instructions = readFileAndProcess(filename) { line ->
			val groups = regex.matchEntire(line)!!.groupValues
			Instruction(
				isOn = groups[1] == "on",
				x = groups[2].toInt()..groups[3].toInt(),
				y = groups[4].toInt()..groups[5].toInt(),
				z = groups[6].toInt()..groups[7].toInt()
			)
		}
	}

	data class Instruction(
		val region: Region,
		val isOn: Boolean
	) {

		constructor(x: IntRange, y: IntRange, z: IntRange, isOn: Boolean) :
			this(Region(x, y, z), isOn)

		infix fun contains(cube: Triple<Int, Int, Int>): Boolean {
			return region contains cube
		}

	}

	data class Region(
		val x: IntRange,
		val y: IntRange,
		val z: IntRange
	) {
		infix fun contains(cube: Triple<Int, Int, Int>): Boolean {
			return (
				cube.first in x
					&& cube.second in y
					&& cube.third in z
				)
		}

		infix fun contains(other: Region): Boolean {
			return (
				this.x contains other.x
					&& this.y contains other.y
					&& this.z contains other.z
				)
		}

		infix fun subtract(other: Region): Set<Region> {
			val xSet = x split other.x
			val ySet = y split other.y
			val zSet = z split other.z

			val result = mutableSetOf<Region>()

			for (_x in xSet) {
				for (_y in ySet) {
					for (_z in zSet) {
						val region = Region(_x, _y, _z)
						if (this contains region && !(other contains region))
							result += region
					}
				}
			}
			return result
		}

		fun getRestrictedRegion(): Region? {
			val newX = x overlap (-50..50)
			val newY = y overlap (-50..50)
			val newZ = z overlap (-50..50)
			if (
				newX == null
				|| newY == null
				|| newZ == null
			) return null

			return Region(newX, newY, newZ)
		}

		fun cubeCount(): Long {
			val x = x.size.toLong()
			val y = y.size.toLong()
			val z = z.size.toLong()
			return x * y * z
		}

		private infix fun IntRange.contains(other: IntRange) =
			this.first <= other.first && this.last >= other.last

		private infix fun IntRange.split(other: IntRange): List<IntRange> {
			val overlap = (this overlap other) ?: return listOf(this, other).filter { it.size > 0 } // No overlap
			return if (overlap == other) { // Full overlap
				listOf(
					first until other.first,
					other,
					other.last + 1..last
				)
			}
			else if (overlap == this) {
				listOf(
					other.first until first,
					this,
					last+1..other.last
				)
			}
			else { // Partial overlap
				if (this.contains(other.first)) { // Right side
					listOf(
						first until other.first,
						other.first..last,
						last+1..other.last
					)
				} else { // Left side
					listOf(
						other.first until first,
						first..other.last,
						other.last+1..last
					)
				}
			}.filter { it.size > 0 }
		}

		private infix fun IntRange.overlap(other: IntRange): IntRange? {
			if (
				(first < other.first && last < other.first)
				|| (first > other.last && last > other.last)
			) {
				return null
			}
			return max(first, other.first)..min(last, other.last)
		}
	}

	fun getRestrictedCubeCount(): Long {
		return getCubeCount()
	}

	fun getFullCubeCount(): Long {
		return getCubeCount(false)
	}

	private fun getCubeCount(isRestricted: Boolean = true): Long {
		var regionSet = setOf<Region>()

		for (i in instructions) {
			println("Regions: ${regionSet.size} Instruction: $i")
			val iRegion = if (isRestricted) {
				i.region.getRestrictedRegion() ?: continue
			} else i.region
			if (i.isOn) {
				if (regionSet.isEmpty()) {
					regionSet = setOf(iRegion)
					continue
				}
				val newRegions = mutableSetOf<Region>()
				for (region in regionSet) {
					newRegions += (region subtract iRegion)
				}
				newRegions += iRegion
				regionSet = newRegions
			} else {
				val newRegions = mutableSetOf<Region>()
				for (region in regionSet) {
					newRegions += (region subtract iRegion)
				}
				regionSet = newRegions
			}
		}

		return regionSet.sumOf { it.cubeCount() }
	}

}