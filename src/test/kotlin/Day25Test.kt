import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day25Test {
	lateinit var solver: Day25
	private val input = "day25"

	@Test
	fun `given test data, movement stops after 58 steps`() {
		solver = Day25(input.test)
		assertEquals(58, solver.getStaticStep())
	}

	@Test
	fun `given test data, we have 26 south fish and 23 east fish`() {
		solver = Day25(input.test)
		assertEquals(26, solver.southFish.size)
		assertEquals(23, solver.eastFish.size)
	}

	@Test
	fun `given a set of east fish, east move results in expected end state`() {
		Day25.colNum = "...>>>>>...".length
		val (eastFish, southFish) = generateFishSet("...>>>>>..>")

		solver = Day25(eastFish, southFish)
		val (newEastFish, _) = generateFishSet(">..>>>>.>..")
		assertEquals(newEastFish to true, solver.eastMove())
	}

	@Test
	fun `given a set of fish, one step results in expected end state`() {
		val map = """
			..........
			.>v....v..
			.......>..
			..........
		""".trimIndent()
		Day25.colNum = "..........".length
		Day25.rowNum = 4
		val (east, south) = generateFishSet(map)
		solver = Day25(east, south)

		val isChanged = solver.step()

		val (newEast, newSouth) = generateFishSet("""
			..........
			.>........
			..v....v>.
			..........
		""".trimIndent())
		assertEquals(newEast, solver.eastFish)
		assertEquals(newSouth, solver.southFish)
		assertTrue { isChanged }
	}

	private fun generateFishSet(init: String): Pair<SortedSet<Day25.Fish>, SortedSet<Day25.Fish>> {
		val eastFish = sortedSetOf<Day25.Fish>()
		val southFish = sortedSetOf<Day25.Fish>()
		var r = 0
		var c = 0
		for (i in init.indices) {
			when (init[i]) {
				'>' -> {
					eastFish += Day25.Fish(r, c)
					c++
				}
				'v' -> {
					southFish += Day25.Fish(r, c)
					c++
				}
				'.' -> c++
				'\n' -> {
					r++
					c = 0
				}
			}
		}
		return eastFish to southFish
	}

	@Test
	fun `solve part 1`() {
		solver = Day25(input.prod)
		println(solver.getStaticStep())
	}
}