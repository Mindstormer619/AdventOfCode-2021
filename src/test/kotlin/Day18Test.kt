import org.junit.Assert.*
import org.junit.Test

class Day18Test {
	lateinit var solver: Day18
	private val input = "day18"

	@Test
	fun `given snail number, we can construct the tree`() {
		val snailNum = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]"
		val result = Day18.SnailNum(snailNum)
		assertEquals(6, result.left?.right?.right?.right?.value)
		assertEquals(4, result.left?.right?.right?.right?.right?.value)
		assertEquals(4, result.rank)
		assertEquals(snailNum, result.stringRepr())
	}

	@Test
	fun `given snail number, we can copy the tree`() {
		val initializer = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]"
		val result = Day18.SnailNum(initializer).copy()
		assertEquals(initializer, result.stringRepr())
	}

	@Test
	fun `given two snail numbers, add gives expected result`() {
		val s1 = Day18.SnailNum("[[[[4,3],4],4],[7,[[8,4],9]]]")
		val s2 = Day18.SnailNum("[1,1]")

		val result = s1 + s2

		assertEquals(5, result.rank)
		assertEquals(1, result.left?.rightmost()?.right?.value)
		assertEquals(9, result.right?.leftmost()?.left?.value)
		assertEquals("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", result.stringRepr())
	}

	@Test
	fun `given a snail number with depth 5 or more, we explode correctly`() {
		val bigSnail = Day18.SnailNum("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")

		bigSnail.explode()
		assertEquals("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]", bigSnail.stringRepr())
		assertEquals(5, bigSnail.rank)

		bigSnail.explode()
		assertEquals("[[[[0,7],4],[15,[0,13]]],[1,1]]", bigSnail.stringRepr())
		assertEquals(4, bigSnail.rank)
	}

	@Test
	fun `given a snail number which is splittable, split works`() {
		val splittableSnail = Day18.SnailNum("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]")
		splittableSnail.explode()
		assertEquals("[[[[0,7],4],[15,[0,13]]],[1,1]]", splittableSnail.stringRepr())

		assertTrue(splittableSnail.split())
		assertEquals("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", splittableSnail.stringRepr())

		assertTrue(splittableSnail.split())
		assertEquals("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]", splittableSnail.stringRepr())

		assertFalse(splittableSnail.split())
	}

	@Test
	fun `given unreduced snail number, reduction works`() {
		val snailNum = Day18.SnailNum("[[[[4,3],4],4],[7,[[8,4],9]]]") + Day18.SnailNum("[1,1]")
		snailNum.reduce()
		assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", snailNum.stringRepr())
	}

	@Test
	fun `given test input, calculate part 1`() {
		solver = Day18(input.test)
		val r = solver.getResult()
		assertEquals("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]", r.stringRepr())
		assertEquals(4140, r.getMagnitude())
	}

	@Test
	fun `solve part 1`() {
		solver = Day18(input.prod)
		println(solver.getResult().getMagnitude())
	}

	@Test
	fun `given test input, biggest magnitude is 3993`() {
		solver = Day18(input.test)
		assertEquals(3993, solver.getBiggestMagnitude())
	}

	@Test
	fun `solve part 2`() {
		solver = Day18(input.prod)
		println(solver.getBiggestMagnitude())
	}
}