import org.junit.Ignore
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
		assertEquals(590784L, solver.getRestrictedCubeCount())
	}

	@Test
	fun `check alt input cube count`() {
		solver = Day22("day22-alt".test)
		assertEquals(39, solver.getRestrictedCubeCount())
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

	@Test
	fun `given two regions subtracted, a point on the edge of the second region is not in the result`() {
		val regionA = Day22.Region(0..10, 0..10, 0..10)
		val regionB = Day22.Region(5..11, 5..11, 5..11)

		val point = Triple(5, 5, 5)

		val result = regionA subtract regionB
		for (r in result) {
			assertFalse("$r contains $point") { r contains point }
		}
	}

	@Test
	fun `solve part 1`() {
		solver = Day22(input.prod)
		println(solver.getRestrictedCubeCount())
	}

	@Test
	fun `test part 1 on new test input`() {
		solver = Day22("$input-large".test)
		assertEquals(474140, solver.getRestrictedCubeCount())
	}

	@Test
	fun `given large input, 2758514936282235 are on in the unrestricted count`() {
		solver = Day22("$input-large".test)
		assertEquals(2758514936282235L, solver.getFullCubeCount())
	}

	@Test @Ignore
	fun `solve part 2`() {
		solver = Day22(input.prod)
		println(solver.getFullCubeCount())
	}
}