import org.junit.Test
import kotlin.test.assertEquals

class Day01Test {
	lateinit var solver: Day01

	@Test
	fun `given test input, we see 7 increases in depth`() {
		solver = Day01("day01".test)
		assertEquals(7, solver.getNumberOfDepthIncreases())
	}

	@Test
	fun `get result of part 1, number of depth increases`() {
		solver = Day01("day01".prod)
		println(solver.getNumberOfDepthIncreases())
	}

	@Test
	fun `given test input, we see 5 increases in sliding window depth`() {
		solver = Day01("day01".test)
		assertEquals(5, solver.getSlidingWindowDepthIncreases())
	}

	@Test
	fun `get result of part 2, sliding window depth increases`() {
		solver = Day01("day01".prod)
		println(solver.getSlidingWindowDepthIncreases())
	}
}