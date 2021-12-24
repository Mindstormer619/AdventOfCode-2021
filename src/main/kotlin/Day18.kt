import java.util.*
import kotlin.math.max

class Day18(filename: String) {
	private val snailNumbers: List<SnailNum>

	init {
		snailNumbers = readFileAndProcess(filename) {
			SnailNum(it)
		}
	}

	fun getResult(): SnailNum {
		var result = snailNumbers[0]
		for (s in snailNumbers.subList(1, snailNumbers.size)) {
			result += s
			result.reduce()
		}
		return result
	}

	fun getBiggestMagnitude(): Long {
		var largestMagnitude = 0L

		for (s1 in snailNumbers) {
			for (s2 in snailNumbers) {
				if (s1 == s2) continue
				var result = s1.copy() + s2.copy()
				result.reduce()
				var magnitude = result.getMagnitude()
				if (largestMagnitude < magnitude) largestMagnitude = magnitude

				result = s2.copy() + s1.copy()
				result.reduce()
				magnitude = result.getMagnitude()
				if (largestMagnitude < magnitude) largestMagnitude = magnitude
			}
		}

		return largestMagnitude
	}


	class SnailNum {
		var left: SnailNum? = null
		var right: SnailNum? = null
		var parent: SnailNum = this
		var value: Int? = null
		var rank = 0

		companion object {
			operator fun invoke(initializer: String): SnailNum {
				val stack = Stack<SnailNum>()
				var prevNum: SnailNum? = null

				for (c in initializer) {
					when {
						c == '[' -> {
							stack += SnailNum()
						}
						c == ']' -> {
							val right = stack.pop()
							val left = stack.pop()
							stack.peek().left = left
							stack.peek().right = right
							stack.peek().rank = max(left.rank, right.rank) + 1
							left.parent = stack.peek()
							right.parent = stack.peek()
						}
						c.isDigit() -> {
							val n = SnailNum()
							n.value = c.digitToInt()
							stack += n

							if (prevNum != null) prevNum.right = n
							n.left = prevNum
							prevNum = n
						}
						c == ',' -> Unit
					}
				}
				return stack.pop()
			}
		}

		fun stringRepr(): String {
			if (value != null) return value.toString()
			return '[' + left!!.stringRepr() + ',' + right!!.stringRepr() + ']'
		}

		operator fun plus(that: SnailNum): SnailNum {
			val result = SnailNum()
			result.left = this
			result.right = that
			result.rank = max(this.rank, that.rank) + 1
			val leftMidpoint = this.rightmost()
			val rightMidpoint = that.leftmost()
			leftMidpoint.right = rightMidpoint
			rightMidpoint.left = leftMidpoint
			this.parent = result
			that.parent = result
			return result
		}

		fun leftmost(): SnailNum {
			var n = this
			while (n.value == null)
				n = n.left!!
			return n
		}

		fun rightmost(): SnailNum {
			var n = this
			while (n.value == null)
				n = n.right!!
			return n
		}

		fun explode() {
			var depth = 0
			var node = this
			while (node.rank + depth >= 5) {
				depth++
				if (node.rank == 1) {
					val leftNeighbor = node.left!!.left
					val lVal = node.left!!.value!!

					val rightNeighbor = node.right!!.right
					val rVal = node.right!!.value!!

					if (leftNeighbor != null) {
						leftNeighbor.right = node
						leftNeighbor.value = leftNeighbor.value!! + lVal
					}
					if (rightNeighbor != null) {
						rightNeighbor.left = node
						rightNeighbor.value = rightNeighbor.value!! + rVal
					}

					node.value = 0
					node.left = leftNeighbor
					node.right = rightNeighbor
					node.rank = 0

					refreshRanks(node)
					break
				}
				node =
					if (node.left!!.rank + depth >= 5) node.left!!
					else node.right!!
			}
		}

		private fun refreshRanks(node: SnailNum) {
			var parent = node.parent
			while (parent != parent.parent) {
				parent.rank = max(parent.left!!.rank, parent.right!!.rank) + 1
				parent = parent.parent
			}
			parent.rank = max(parent.left!!.rank, parent.right!!.rank) + 1
		}

		fun split(): Boolean {
			var num: SnailNum? = this.leftmost()
			while (num != null) {
				val value = num.value!!
				if (value > 9) {
					val newLeft = value / 2
					val newRight = value - newLeft

					val left = SnailNum()
					left.value = newLeft
					left.parent = num
					val right = SnailNum()
					right.value = newRight
					right.parent = num
					num.value = null

					val leftNeighbor = num.left
					val rightNeighbor = num.right

					if (leftNeighbor != null) leftNeighbor.right = left
					if (rightNeighbor != null) rightNeighbor.left = right

					left.left = leftNeighbor
					left.right = right
					right.right = rightNeighbor
					right.left = left

					num.left = left
					num.right = right
					num.rank = 1
					refreshRanks(num)
					return true
				}
				num = num.right
			}
			return false
		}

		fun reduce() {
			while (true) {
				val rank = this.rank
				if (rank >= 5) {
					explode()
					continue
				}
				if (split()) continue
				break
			}
		}

		fun getMagnitude(): Long {
			if (value != null) return value!!.toLong()
			return (3 * left!!.getMagnitude()) + (2 * right!!.getMagnitude())
		}

		private fun shallowCopy(): SnailNum {
			val s = SnailNum()
			s.value = value
			s.rank = rank
			return s
		}

		fun copy(): SnailNum {
			val oldStack = Stack<SnailNum>()
			val newStack = Stack<SnailNum>()
			var prevNumeric: SnailNum? = null

			oldStack += this
			val newRoot = this.shallowCopy()
			newStack += newRoot
			while (oldStack.isNotEmpty() && newStack.isNotEmpty()) {
				val oldNode = oldStack.pop()
				val newNode = newStack.pop()
				if (oldNode.value == null) {
					// has children
					newNode.left = oldNode.left!!.shallowCopy().apply { parent = newNode }
					newNode.right = oldNode.right!!.shallowCopy().apply { parent = newNode }

					oldStack += oldNode.right!!
					oldStack += oldNode.left!!
					newStack += newNode.right!!
					newStack += newNode.left!!
				}
				else {
					// has no children
					newNode.left = prevNumeric
					if (prevNumeric != null) prevNumeric.right = newNode
					prevNumeric = newNode
				}
			}
			return newRoot
		}
	}


}