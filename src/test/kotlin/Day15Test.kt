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
		assertEquals(11, solver.getLowestRisk())
	}
}