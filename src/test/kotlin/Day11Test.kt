import org.junit.Test
import kotlin.test.assertEquals

class Day11Test {
	lateinit var solver: Day11
	private val input = "day11"

	@Test
	fun `given test input, 10 steps later we have 204 flashes`() {
		solver = Day11(input.test)
		assertEquals(204, solver.simulateFlashes(10))
	}

	@Test
	fun `given test input, 100 steps later we have 1656 flashes`() {
		solver = Day11(input.test)
		assertEquals(1656, solver.simulateFlashes(100))
	}

	@Test
	fun `solve part 1`() {
		solver = Day11(input.prod)
		println(solver.simulateFlashes(100))
	}

	@Test
	fun `given test input, full flash occurs at step 195`() {
		solver = Day11(input.test)
		assertEquals(195, solver.fullFlash(999))
	}

	@Test
	fun `solve part 2`() {
		solver = Day11(input.prod)
		println(solver.fullFlash(999))
	}
}