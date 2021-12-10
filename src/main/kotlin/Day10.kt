import java.util.*

class Day10(filename: String) {
	private val patterns: List<String>

	init {
		patterns = readFileAndProcess(filename) { it }
	}

	fun getErrorScore(): Int {
		var errorScore = 0
		for (pattern in patterns) {
			val corruptedCharacter = pattern.getProcessedResult()
			if (corruptedCharacter is CharResult) {
				errorScore += when (corruptedCharacter.c) {
					')' -> 3
					']' -> 57
					'}' -> 1197
					'>' -> 25137
					else -> throw NotImplementedError(corruptedCharacter.toString())
				}
			}
		}
		return errorScore
	}

	fun getMiddleScore(): Long {
		val scores = mutableListOf<Long>()
		for (pattern in patterns) {
			val stackResult = pattern.getProcessedResult()
			if (stackResult !is StackResult) continue
			var score = 0L
			while (!stackResult.s.empty()) {
				score *= 5
				score += when (stackResult.s.pop()) {
					'(' -> 1
					'[' -> 2
					'{' -> 3
					'<' -> 4
					else -> throw NotImplementedError()
				}
			}
			scores.add(score)
		}
		scores.sort()
		return scores[scores.size / 2]
	}

	private fun String.getProcessedResult(): PatternResult {
		val charStack = Stack<Char>()
		val opens = setOf('(', '[', '{', '<')
		val closes = mapOf(
			')' to '(',
			']' to '[',
			'}' to '{',
			'>' to '<',
		)

		for (c in this) {
			if (c in opens) charStack.push(c)
			else {
				val latestOpen = if (charStack.empty()) null else charStack.pop()
				if (latestOpen == null || closes[c] != latestOpen) return CharResult(c)
			}
		}
		return StackResult(charStack)
	}

	sealed class PatternResult
	data class CharResult(val c: Char) : PatternResult()
	data class StackResult(val s: Stack<Char>) : PatternResult()
}
