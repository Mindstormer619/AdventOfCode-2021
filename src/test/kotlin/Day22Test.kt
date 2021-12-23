import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day22Test {
	lateinit var solver: Day22
	private val input = "day22"

	@Test
	fun `given test input, we have 590784 cubes`() {
		solver = Day22(input.test)
		assertEquals(590784L, solver.getCubeCount())
	}

	@Test
	fun `check alt input cube count`() {
		solver = Day22("day22-alt".test)
		assertEquals(39, solver.getCubeCount())
	}

	@Test
	fun `given two points and an instruction, contains works correctly`() {
		val insidePoint = Triple(5,6,7)
		val outsidePoint = Triple(8, 9, 45)

		val instruction = Day22.Instruction(0..10, 0..10, 0..10, false)

		assertTrue { instruction contains insidePoint }
		assertFalse { instruction contains outsidePoint }
	}

	@Test
	fun `given two regions, we can compare if one is inside the other`() {
		val region1 = Day22.Region(0..10, 0..10, 0..10)
		val region2 = Day22.Region(5..10, 5..10, 5..10)

		assertTrue { region1 contains region2 }
	}

	@Test
	fun `given two additive regions, result contains all regions expected`() {
		val region1 = Day22.Region(0..10, 0..10, 0..10)
		val region2 = Day22.Region(5..11, 5..11, 5..11)

		val result = region1 add region2
		assertEquals(8, result.size)
		for (r in result) {
			val a = region1 contains r
			val b = region2 contains r
			assertTrue("$r is in r1: $a r2: $b") {a xor b}
		}
	}

	@Test
	fun `given two regions, subtract plus the second region gives a nonoverlapping set`() {
		val regionA = Day22.Region(0..10, 0..10, 0..10)
		val regionB = Day22.Region(5..11, 5..11, 5..11)

		val result = regionA subtract regionB
		var countB = 0
		for (r in (result + regionB)) {
			val a = regionA contains r
			val b = regionB contains r
			if (b) countB++
			assertTrue("$r is in r1: $a r2: $b") { a xor b }
		}
		assertEquals(1, countB)
	}
}