import org.junit.Test
import kotlin.test.assertEquals

class Day15Test {
	lateinit var solver: Day15
	private val input = "day15"

	@Test
	fun `given test input, lowest risk is 40`() {
		solver = Day15(input.test)
		assertEquals(40, solver.getLowestRisk())
	}

	@Test
	fun `given alt input, lowest risk is 11`() {
		solver = Day15("day15-alt".test)
		assertEquals(10, solver.getLowestRisk())
	}

	@Test
	fun `solve part 1`() {
		solver = Day15(input.prod)
		println(solver.getLowestRisk())
	}

	@Test
	fun `given test input, scaled risk is 315`() {
		solver = Day15(input.test)
		assertEquals(315, solver.getScaledRisk())
	}

	@Test
	fun `solve part 2`() {
		solver = Day15(input.prod)
		println(solver.getScaledRisk())
	}
}