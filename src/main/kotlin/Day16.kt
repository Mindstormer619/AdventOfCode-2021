import kotlin.properties.Delegates

class Day16(hex: String) {
	private val bytes = hex.map { hexTable(it) }.joinToString("")

	fun getVersionSum() = Parser(bytes).parse().versionSum()
	fun getBitsValue() = Parser(bytes).parse().getBitsValue()

	class Parser(private val bytes: String) {
		fun parse(startIndex: Int = 0): Packet {
			var state: State = VersionId(bytes, startIndex)

			val packetBuilder = Packet.Builder()
			while (state !is Terminal) {
				when (state) {
					is VersionId -> packetBuilder.versionId = state.process()
					is TypeId -> packetBuilder.typeId = state.process()
					is LiteralGroup -> {
						var value = packetBuilder.value
						if (value == null) value = 0
						value = value shl 4
						value += state.process()
						packetBuilder.value = value
					}
					is Operator -> {
						val value = state.process()
						val subpackets = mutableListOf<Packet>()
						var index = state.index
						if (state.isLengthType) {
							val end = index + value
							while (index < end) {
								val packet = parse(index)
								subpackets += packet
								index = packet.endsAt
							}
						} else {
							for (i in 1..value) {
								val packet = parse(index)
								subpackets += packet
								index = packet.endsAt
							}
						}
						state.index = index
						packetBuilder.subpackets = subpackets
					}
					is Terminal -> throw NotImplementedError()
				}
				state = state.next()
			}
			packetBuilder.endsAt = state.process()
			return packetBuilder.build()
		}

		private sealed class State(val bytes: String, var index: Int) {
			abstract fun process(): Int
			abstract fun next(): State
		}

		private class VersionId(bytes: String, index: Int) : State(bytes, index) {
			override fun process(): Int {
				val versionId = bytes.substring(index until index + 3).toInt(2)
				index += 3
				return versionId
			}

			override fun next(): State {
				return TypeId(bytes, index)
			}
		}

		private class TypeId(bytes: String, index: Int) : State(bytes, index) {
			var typeId by Delegates.notNull<Int>()
			override fun process(): Int {
				typeId = bytes.substring(index until index + 3).toInt(2)
				index += 3
				return typeId
			}

			override fun next(): State {
				return if (typeId == 4) {
					LiteralGroup(bytes, index)
				} else {
					Operator(bytes, index)
				}
			}
		}

		private class Operator(bytes: String, index: Int) : State(bytes, index) {
			var isLengthType = false
			override fun process(): Int {
				isLengthType = bytes[index++] == '0'
				val value =
					if (isLengthType) bytes.substring(index until index + 15).toInt(2)
					else bytes.substring(index until index + 11).toInt(2)
				index += if (isLengthType) 15 else 11
				return value
			}

			override fun next(): State {
				return Terminal(bytes, index)
			}

		}

		private class LiteralGroup(bytes: String, index: Int) : State(bytes, index) {
			var isTerminating: Boolean = true
			override fun process(): Int {
				if (bytes[index] == '1') isTerminating = false
				val value = bytes.substring(index + 1..index + 4).toInt(2)
				index += 5
				return value
			}

			override fun next(): State {
				return if (isTerminating) Terminal(bytes, index)
				else LiteralGroup(bytes, index)
			}
		}

		private class Terminal(bytes: String, index: Int) : State(bytes, index) {
			override fun process(): Int {
				return index
			}

			override fun next(): State {
				throw NotImplementedError()
			}
		}
	}

	class Packet(
		val versionId: Int,
		val typeId: Int,
		val endsAt: Int,
		val value: Long?,
		val subpackets: List<Packet>
	) {
		class Builder {
			var versionId by Delegates.notNull<Int>()
			var typeId by Delegates.notNull<Int>()
			var endsAt by Delegates.notNull<Int>()

			// literal packet
			var value: Long? = null

			// subpackets
			var subpackets = listOf<Packet>()

			fun build(): Packet {
				return Packet(versionId, typeId, endsAt, value, subpackets)
			}
		}

		fun versionSum(): Int = versionId + subpackets.sumOf { it.versionSum() }

		fun getBitsValue(): Long = when (typeId) {
			0 -> subpackets.sumOf { it.getBitsValue() }
			1 -> subpackets.fold(1) { acc, p -> acc * p.getBitsValue() }
			2 -> subpackets.minOf { it.getBitsValue() }
			3 -> subpackets.maxOf { it.getBitsValue() }
			4 -> value!!.toLong()
			5 -> if (subpackets[0].getBitsValue() > subpackets[1].getBitsValue()) 1L else 0L
			6 -> if (subpackets[0].getBitsValue() < subpackets[1].getBitsValue()) 1L else 0L
			7 -> if (subpackets[0].getBitsValue() == subpackets[1].getBitsValue()) 1L else 0L
			else -> throw NotImplementedError("typeId: $typeId")
		}
	}

	companion object {
		private val hexTable = mapOf(
			'0' to "0000",
			'1' to "0001",
			'2' to "0010",
			'3' to "0011",
			'4' to "0100",
			'5' to "0101",
			'6' to "0110",
			'7' to "0111",
			'8' to "1000",
			'9' to "1001",
			'A' to "1010",
			'B' to "1011",
			'C' to "1100",
			'D' to "1101",
			'E' to "1110",
			'F' to "1111"
		)
	}
}