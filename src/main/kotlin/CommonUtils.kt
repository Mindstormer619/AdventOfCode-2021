import java.io.File

fun <T> readFileAndProcess(filePath: String, transform: (String) -> T): List<T> =
    File(filePath).useLines { sequence -> sequence.map(transform).toList() }

infix fun Int.mod(other: Int) = Math.floorMod(this, other)

infix fun Int.towards(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

operator fun <K, V> Map<K, V>.invoke(key: K) = this.getValue(key)

fun List<List<Int>>.check(r: Int, c: Int): Int? =
    if (r !in this.indices || c !in this[r].indices) null
    else this[r][c]

val IntRange.size: Int
    get() = if (isEmpty()) 0 else (last - first + 1)