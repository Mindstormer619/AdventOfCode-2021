import org.junit.Test
import kotlin.test.assertEquals

class Day09Test {
	lateinit var solver: Day09
	private val input = "day09"

	@Test
	fun `given test input, total risk level is 15`() {
		solver = Day09(input.test)
		assertEquals(15, solver.getTotalRiskLevel())
	}

	@Test
	fun `solve part 1`() {
		solver = Day09(input.prod)
		println(solver.getTotalRiskLevel())
	}

	@Test
	fun `given test input, largest basin product is 1134`() {
		solver = Day09(input.test)
		assertEquals(1134L, solver.getBasinProduct())
	}

	@Test
	fun `solve part 2`() {
		solver = Day09(input.prod)
		println(solver.getBasinProduct())
	}
}