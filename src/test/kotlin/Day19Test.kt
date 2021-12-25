import org.junit.Test
import kotlin.test.assertEquals

class Day19Test {
	lateinit var solver: Day19
	private val input = "day19"

	@Test
	fun `test input read`() {
		solver = Day19(input.test)
		assertEquals(5, solver.data.size)
		assertEquals(26, solver.data[4].size)
	}

	@Test
	fun `given test input, there are 79 beacons`() {
		solver = Day19(input.test)
		assertEquals(79, solver.countBeacons())
	}
}