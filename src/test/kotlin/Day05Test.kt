import org.junit.Assert.*
import org.junit.Test

class Day05Test {
	lateinit var solver: Day05

	@Test
	fun `given test input, there are 5 dangerous points`() {
		solver = Day05("day05".test)
		assertEquals(5, solver.getDangerousPoints())
	}

	@Test
	fun `solve part 1`() {
		solver = Day05("day05".prod)
		println(solver.getDangerousPoints())
	}

	@Test
	fun `given test input, there are 12 dangerous points including diagonals`() {
		solver = Day05("day05".test)
		assertEquals(12, solver.getAllDangerousPoints())
	}

	@Test
	fun `solve part 2`() {
		solver = Day05("day05".prod)
		println(solver.getAllDangerousPoints())
	}
}