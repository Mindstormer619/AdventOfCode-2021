import org.junit.Assert.*
import org.junit.Test

class Day03Test {
	lateinit var solver: Day03

	@Test
	fun `given test input, power consumption is 198`() {
		solver = Day03("day03".test)
		assertEquals(198, solver.getPowerConsumption())
	}

	@Test
	fun `solve part 1`() {
		solver = Day03("day03".prod)
		println(solver.getPowerConsumption())
	}

	@Test
	fun `given test input, life support rating is 230`() {
		solver = Day03("day03".test)
		assertEquals(230, solver.getLifeSupportRating())
	}

	@Test
	fun `solve part 2`() {
		solver = Day03("day03".prod)
		println(solver.getLifeSupportRating())
	}
}