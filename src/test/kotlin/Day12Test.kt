import org.junit.Test
import kotlin.test.assertEquals

class Day12Test {
	lateinit var solver: Day12
	private val input = "day12"

	@Test
	fun `given test input, 19 paths exist`() {
		solver = Day12(input.test)
		assertEquals(19, solver.getNumberOfPaths())
	}

	@Test
	fun `given larger test input, 226 paths exist`() {
		solver = Day12("day12-larger".test)
		assertEquals(226, solver.getNumberOfPaths())
	}

	@Test
	fun `solve part 1`() {
		solver = Day12("day12".prod)
		println(solver.getNumberOfPaths())
	}

	@Test
	fun `given test input, new path count is 103`() {
		solver = Day12(input.test)
		assertEquals(103, solver.getPathsAllowingTwoVisits())
	}

	@Test
	fun `given larger test input, new path count is 3509`() {
		solver = Day12("day12-larger".test)
		assertEquals(3509, solver.getPathsAllowingTwoVisits())
	}

	@Test
	fun `solve part 2`() {
		solver = Day12(input.prod)
		println(solver.getPathsAllowingTwoVisits())
	}
}