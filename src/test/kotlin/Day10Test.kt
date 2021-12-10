import org.junit.Test
import kotlin.test.assertEquals

class Day10Test {
	lateinit var solver: Day10
	private val input = "day10"

	@Test
	fun `given test input, error score is 26397`() {
		solver = Day10(input.test)
		assertEquals(26397, solver.getErrorScore())
	}

	@Test
	fun `solve part 1`() {
		solver = Day10(input.prod)
		println(solver.getErrorScore())
	}


	@Test
	fun `given test input, middle score is 288957`() {
		solver = Day10(input.test)
		assertEquals(288957L, solver.getMiddleScore())
	}

	@Test
	fun `solve part 2`() {
		solver = Day10(input.prod)
		println(solver.getMiddleScore())
	}
}