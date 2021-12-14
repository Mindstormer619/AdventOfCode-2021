import org.junit.Test
import kotlin.test.assertEquals

class Day14Test {
	lateinit var solver: Day14
	private val input = "day14"

	@Test
	fun `given test input, elemental difference is 1588`() {
		solver = Day14(input.test)
		assertEquals(1588, solver.getElementalDifference())
	}

	@Test
	fun `given alt input, run code`() {
		solver = Day14("day14-alt".test)
		assertEquals(1, solver.getElementalDifference(2))
	}

	@Test
	fun `solve part 1`() {
		solver = Day14(input.prod)
		println(solver.getElementalDifference())
	}

	@Test
	fun `given test input, elemental difference after 40 steps is 2188189693529L`() {
		solver = Day14(input.test)
		assertEquals(2188189693529L, solver.getElementalDifference(40))
	}

	@Test
	fun `solve part 2`() {
		solver = Day14(input.prod)
		println(solver.getElementalDifference(40))
	}
}