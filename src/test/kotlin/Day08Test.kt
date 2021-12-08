import org.junit.Test
import kotlin.test.assertEquals

class Day08Test {
	lateinit var solver: Day08
	private val input = "day08"

	@Test
	fun `given test input, count for 1 4 7 8 is 26`() {
		solver = Day08(input.test)
		assertEquals(26, solver.get1478Count())
	}

	@Test
	fun `solve part 1`() {
		solver = Day08(input.prod)
		println(solver.get1478Count())
	}

	@Test
	fun `given test data, total output value is 61229`() {
		solver = Day08(input.test)
		assertEquals(61229, solver.getTotalOutput())
	}

	@Test
	fun `solve part 2`() {
		solver = Day08(input.prod)
		println(solver.getTotalOutput())
	}
}