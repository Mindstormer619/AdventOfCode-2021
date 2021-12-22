import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day17Test {
	lateinit var solver: Day17
	private val testData = "target area: x=20..30, y=-10..-5"
	private val prodData = "target area: x=277..318, y=-92..-53"

	@Test
	fun `given test data, max Y is 45`() {
		solver = Day17(testData)
		assertEquals(45, solver.getMaxY())
	}

	@Test
	fun `given test data, yVelocities has 9 as an acceptable velocity`() {
		solver = Day17(testData)
		val yVelocities = solver.findYVelocity()
		println(yVelocities.keys)
		assertEquals(1, yVelocities.filter { 9 in it.value }.size)
	}

	@Test
	fun `given test data, xVelocities has 6 as an acceptable velocity`() {
		solver = Day17(testData)
		val times = setOf(5, 6, 7, 9, 10, 12, 14, 16, 18, 20)
		val xVelocities = solver.findXVelocity(times)
		assertTrue { xVelocities(6).contains(6) }
	}

	@Test
	fun `given test data, velocities contains (6,9)`() {
		solver = Day17(testData)
		val velocities = solver.getVelocities()
		assertTrue { (6 to 9) in velocities }
		assertTrue { (7 to 2) in velocities }
		assertTrue { (6 to 3) in velocities }
		assertTrue { (17 to -4) !in velocities }
	}

	@Test
	fun `solve part 1`() {
		solver = Day17(prodData)
		println(solver.getMaxY())
	}

	@Test
	fun `given test data, 112 unique velocities`() {
		solver = Day17(testData)
		assertEquals(112, solver.getVelocities().toSet().size)
	}

	@Test
	fun `solve part 2`() {
		solver = Day17(prodData)
		println(solver.getVelocities().toSet().size)
	}
}