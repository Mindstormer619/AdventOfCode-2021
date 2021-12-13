import org.junit.Test
import kotlin.test.assertEquals

class Day13Test {
	lateinit var solver: Day13
	private val input = "day13"

	@Test
	fun `given test input, first fold has 17 dots`() {
		solver = Day13(input.test)
		assertEquals(17, solver.getDots(1))
	}

	@Test
	fun `solve part 1`() {
		solver = Day13(input.prod)
		println(solver.getDots(1))
	}

	@Test
	fun `given test input, print result of all folds`() {
		solver = Day13(input.test)
		solver.printDots()
	}

	@Test
	fun `solve part 2`() {
		solver = Day13(input.prod)
		solver.printDots()
	}
}