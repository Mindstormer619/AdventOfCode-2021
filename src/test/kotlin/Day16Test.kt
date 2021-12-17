import org.junit.Test
import kotlin.test.assertEquals

class Day16Test {
	lateinit var solver: Day16
	private val input = "day16"
	private val hex = readFileAndProcess(input.prod) {it}[0]

	@Test
	fun `given test data 1, version sum is 16`() {
		solver = Day16("8A004A801A8002F478")
		assertEquals(16, solver.getVersionSum())
	}

	@Test
	fun `given test data 2, version sum is 31`() {
		solver = Day16("A0016C880162017C3686B18A3D4780")
		assertEquals(31, solver.getVersionSum())
	}

	@Test
	fun `given a packet, extract the version ID and type ID`() {
		val parser = Day16.Parser("110100101111111000101000")
		val packet = parser.parse()

		assertEquals(6, packet.versionId)
		assertEquals(4, packet.typeId)
	}

	@Test
	fun `given a literal packet, we read the value as 2021`() {
		val parser = Day16.Parser("110100101111111000101000")
		val packet = parser.parse()

		assertEquals(2021, packet.value)
	}

	@Test
	fun `given an operator packet of Length type, we have 2 subpackets`() {
		val parser = Day16.Parser("00111000000000000110111101000101001010010001001000000000")
		val packet = parser.parse()

		assertEquals(2, packet.subpackets.size)
	}

	@Test
	fun `given an operator packet of Count type, we have 3 subpackets`() {
		val parser = Day16.Parser("11101110000000001101010000001100100000100011000001100000")
		val packet = parser.parse()

		assertEquals(3, packet.subpackets.size)
	}

	@Test
	fun `solve part 1`() {
		solver = Day16(hex)
		println(solver.getVersionSum())
	}

	@Test
	fun `given sum hex, sum is 3`() {
		solver = Day16("C200B40A82")
		assertEquals(3, solver.getBitsValue())
	}

	@Test
	fun `given product hex, value is 54`() {
		solver = Day16("04005AC33890")
		assertEquals(54, solver.getBitsValue())
	}

	@Test
	fun `given min packet, value is 7`() {
		solver = Day16("880086C3E88112")
		assertEquals(7, solver.getBitsValue())
	}

	@Test
	fun `given max hex, value is 9`() {
		solver = Day16("CE00C43D881120")
		assertEquals(9, solver.getBitsValue())
	}

	@Test
	fun `given less-than packet, we get 1`() {
		solver = Day16("D8005AC2A8F0")
		assertEquals(1, solver.getBitsValue())
	}

	@Test
	fun `given greater-than hex, value is 0`() {
		solver = Day16("F600BC2D8F")
		assertEquals(0, solver.getBitsValue())
	}

	@Test
	fun `given equals hex, we get 0`() {
		solver = Day16("9C005AC2F8F0")
		assertEquals(0, solver.getBitsValue())
	}

	@Test
	fun `given nested hex, value is 1`() {
		solver = Day16("9C0141080250320F1802104A08")
		assertEquals(1, solver.getBitsValue())
	}

	@Test
	fun `solve part 2`() {
		solver = Day16(hex)
		println(solver.getBitsValue())
	}
}