import java.util.*
import kotlin.properties.Delegates

class Day25(var eastFish: SortedSet<Fish>, var southFish: SortedSet<Fish>) {

	companion object {
		var colNum by Delegates.notNull<Int>()
		var rowNum by Delegates.notNull<Int>()

		operator fun invoke(filename: String): Day25 {
			var r = 0
			val eastFish = sortedSetOf<Fish>()
			val southFish = sortedSetOf<Fish>()
			readFileAndProcess(filename) { line ->
				colNum = line.length
				var c = 0
				for (fish in line) {
					when (fish) {
						'>' -> eastFish += Fish(r, c)
						'v' -> southFish += Fish(r, c)
						'.' -> Unit
					}
					c++
				}
				r++
			}
			rowNum = r
			return Day25(eastFish, southFish)
		}
	}

	data class Fish(val r: Int, val c: Int) : Comparable<Fish> {
		override fun compareTo(other: Fish) = (r - other.r) * colNum + (c - other.c)
		fun east(): Fish = Fish(r, (c + 1) mod colNum)
		fun south(): Fish = Fish((r + 1) mod rowNum, c)
	}

	fun getStaticStep(): Int {
		var stepNum = 1
		while(true) {
			val changed = step()
			if (!changed) return stepNum
			stepNum++
		}
	}

	fun eastMove(): Pair<TreeSet<Fish>, Boolean> {
		val newEastFish = sortedSetOf<Fish>()
		var changed = false
		for (fish in eastFish) {
			if (fish.east() in eastFish || fish.east() in southFish) {
				newEastFish += fish
			} else {
				newEastFish += fish.east()
				changed = true
			}
		}
		return newEastFish to changed
	}

	private fun southMove(): Pair<TreeSet<Fish>, Boolean> {
		val newSouthFish = sortedSetOf<Fish>()
		var changed = false
		for (fish in southFish) {
			if (fish.south() in eastFish || fish.south() in southFish) {
				newSouthFish += fish
			} else {
				newSouthFish += fish.south()
				changed = true
			}
		}
		return newSouthFish to changed
	}

	fun step(): Boolean {
		val (eastFish, eastChanged) = eastMove()
		this.eastFish = eastFish
		val (southFish, southChanged) = southMove()
		this.southFish = southFish
		return eastChanged || southChanged
	}
}