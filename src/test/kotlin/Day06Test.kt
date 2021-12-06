import org.junit.Test
import kotlin.test.assertEquals

class Day06Test {
	lateinit var solver: Day06

	@Test
	fun `given test input, number of fish is as provided`() {
		solver = Day06("day06".test)
		assertEquals(26, solver.getNumberOfFish(18))
		assertEquals(5934, solver.getNumberOfFish(80))
	}

	@Test
	fun `solve part 1`() {
		solver = Day06("day06".prod)
		println(solver.getNumberOfFish(80))
	}

	@Test
	fun `given test input, we have a ton of fish after 256 days`() {
		solver = Day06("day06".test)
		assertEquals(26984457539L, solver.getNumberOfFish(256))
	}

	@Test
	fun `solve part 2`() {
		solver = Day06("day06".prod)
		println(solver.getNumberOfFish(256))
	}
}