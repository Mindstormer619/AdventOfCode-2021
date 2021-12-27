@file:Suppress("LocalVariableName")

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day20Test {
	lateinit var solver: Day20
	private val input = "day20"

	@Test
	fun `given test data, 35 pixels are lit after 2x processing`() {
		solver = Day20(input.test)
		assertEquals(35, solver.getNumberOfPixels(2))
	}

	@Test
	fun `given test data, input is read correctly`() {
		solver = Day20(input.test)
		assertFalse { (1 to 1) in solver.activePoints }
		assertTrue { (3 to 2) in solver.activePoints }
		assertEquals(10, solver.activePoints.size)
	}

	@Test
	fun `given test data, getting the enhancer index for a given pixel works`() {
		solver = Day20(input.test)

		val neighbors1_1 = "#..#..##.".map { if (it == '#') '1' else '0' }.joinToString("").toInt(2)
		assertEquals(neighbors1_1, solver.getEnhancerIndex(1 to 1))

		solver.isInfinityLit = true
		val neighbors0_3 = "###.#....".map { if (it == '#') '1' else '0' }.joinToString("").toInt(2)
		assertEquals(neighbors0_3, solver.getEnhancerIndex(0 to 3))
	}

	@Test
	fun `solve part 1`() {
		solver = Day20(input.prod)
		println(solver.getNumberOfPixels(2))
	}

	@Test
	fun `given test data, 50 enhancement passes results in 3351 pixels on`() {
		solver = Day20(input.test)
		assertEquals(3351, solver.getNumberOfPixels(50))
	}

	@Test
	fun `solve part 2`() {
		solver = Day20(input.prod)
		println(solver.getNumberOfPixels(50))
	}
}