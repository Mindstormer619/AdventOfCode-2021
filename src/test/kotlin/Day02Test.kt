import org.junit.Assert.*
import org.junit.Test

class Day02Test {
	private lateinit var solver: Day02

	@Test
	fun `given test input, final position number is 150`() {
		solver = Day02("day02".test)
		assertEquals(150L, solver.getFinalPositionNumber())
	}

	@Test
	fun `print part 1 solution`() {
		solver = Day02("day02".prod)
		println(solver.getFinalPositionNumber())
	}

	@Test
	fun `given test input, aim position number is 900`() {
		solver = Day02("day02".test)
		assertEquals(900L, solver.getAimPositionNumber())
	}

	@Test
	fun `print part 2 solution`() {
		solver = Day02("day02".prod)
		println(solver.getAimPositionNumber())
	}
}