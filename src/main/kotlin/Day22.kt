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

		infix fun add(other: Region): Set<Region> {
			val xSet = x combine other.x
			val ySet = y combine other.y
			val zSet = z combine other.z

			val result = mutableSetOf(this)

			for (_x in xSet) {
				for (_y in ySet) {
					for (_z in zSet) {
						val region = Region(_x, _y, _z)
						if (other contains region && !(this contains region))
							result += region
					}
				}
			}
			return result
		}

		infix fun subtract(other: Region): Set<Region> {
			val xSet = x combine other.x
			val ySet = y combine other.y
			val zSet = z combine other.z

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
			val x = max(x.size().toLong() - 2, 0)
			val y = max(y.size().toLong() - 2, 0)
			val z = max(z.size().toLong() - 2, 0)
			return x * y * z
		}

		private infix fun IntRange.contains(other: IntRange) =
			this.first <= other.first && this.last >= other.last

		private infix fun IntRange.combine(other: IntRange): List<IntRange> {
			val (a, b, c, d) = listOf(this.first, this.last, other.first, other.last).sorted()
			return listOf(a..b, b..c, c..d)
		}

		infix fun IntRange.overlap(other: IntRange): IntRange? {
			if (
				(first < other.first && last < other.first)
				|| (first > other.last && last > other.last)
			) {
				return null
			}
			return max(first, other.first)..min(last, other.last)
		}

		private fun IntRange.size() = last - first + 1
	}

	fun getCubeCount(): Long {
		var regionSet = setOf<Region>()

		for (i in instructions) {
			val iRegion = i.region.getRestrictedRegion() ?: continue
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
			}
			else {
				val newRegions = mutableSetOf<Region>()
				for (region in regionSet) {
					newRegions += (region subtract iRegion)
				}
				regionSet = newRegions
			}
		}

		val internalCount = regionSet.sumOf { it.cubeCount() }

		val edgeVertices = mutableSetOf<Triple<Int, Int, Int>>()
		for (region in regionSet) {
			for (x in region.x) {
				edgeVertices += Triple(x, region.y.first, region.z.first)
				edgeVertices += Triple(x, region.y.first, region.z.last)
				edgeVertices += Triple(x, region.y.last, region.z.first)
				edgeVertices += Triple(x, region.y.last, region.z.last)
			}
			for (y in region.y) {
				edgeVertices += Triple(region.x.first, y, region.z.first)
				edgeVertices += Triple(region.x.first, y, region.z.last)
				edgeVertices += Triple(region.x.last, y, region.z.first)
				edgeVertices += Triple(region.x.last, y, region.z.last)
			}
			for (z in region.z) {
				edgeVertices += Triple(region.x.first, region.y.first, z)
				edgeVertices += Triple(region.x.first, region.y.last, z)
				edgeVertices += Triple(region.x.last, region.y.first, z)
				edgeVertices += Triple(region.x.last, region.y.last, z)
			}
		}
		return internalCount + edgeVertices.size
	}

}