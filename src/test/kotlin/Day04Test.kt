import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day04Test {
	lateinit var solver: Day04

	@Test
	fun `given test input, final score is 4512`() {
		solver = Day04("day04".test)
		assertEquals(4512L, solver.getBingoScore())
	}

	@Test
	fun `adding a row to a new board works`() {
		val board = Day04.Board()

		board.addRow(listOf(1, 2, 3, 4, 5))
		assertEquals(5, board.rows.first().size)
	}

	@Test
	fun `given a board containing a called out number, number is checked off`() {
		val board = Day04.Board()
		board.addRow(listOf(1,2,3))
		board.addRow(listOf(4,5,6))
		board.addRow(listOf(7,8,9))

		board.checkOff(6)
		assertTrue(board.rows[1][2].checked)
	}

	@Test
	fun `given a bingo-reaching number is called, true is returned`() {
		val board = Day04.Board()
		board.addRow(listOf(1,2,3))
		board.addRow(listOf(4,5,6))
		board.addRow(listOf(7,8,9))

		assertFalse(board.checkOff(4))
		assertFalse(board.checkOff(6))
		assertTrue(board.checkOff(5))
	}

	@Test
	fun `given a number is not found in a board, checkOff is false`() {
		val board = Day04.Board()
		board.addRow(listOf(1,2,3))
		board.addRow(listOf(4,5,6))
		board.addRow(listOf(7,8,9))

		assertFalse { board.checkOff(10) }
	}

	@Test
	fun `solve part 1`() {
		solver = Day04("day04".prod)
		println(solver.getBingoScore())
	}

	// --------------------------------------------------------
	// Part 2
	// --------------------------------------------------------

	@Test
	fun `given test input, last board to be solved has score of 1924`() {
		solver = Day04("day04".test)
		assertEquals(1924, solver.getLastBoardScore())
	}

	@Test
	fun `solve part 2`() {
		solver = Day04("day04".prod)
		println(solver.getLastBoardScore())
	}
}