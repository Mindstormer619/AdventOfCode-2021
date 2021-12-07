import org.junit.Test
import kotlin.test.assertEquals

class Day07Test {
	lateinit var solver: Day07

	@Test
	fun `given test data, minimum fuel used is 37`() {
		solver = Day07("day07".test)
		assertEquals(37, solver.getMinimumFuelUsed())
	}

	@Test
	fun `solve part 1`() {
		solver = Day07("day07".prod)
		println(solver.getMinimumFuelUsed())
	}

	@Test
	fun `given test data, nonlinear fuel use is 168`() {
		solver = Day07("day07".test)
		assertEquals(168, solver.getMinimumFuelUsed(Day07.FuelUse::nonlinearFuelUseFormula))
	}

	@Test
	fun `solve part 2`() {
		solver = Day07("day07".prod)
		println(solver.getMinimumFuelUsed(Day07.FuelUse::nonlinearFuelUseFormula))
	}
}