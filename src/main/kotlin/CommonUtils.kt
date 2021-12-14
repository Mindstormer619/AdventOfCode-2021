import java.io.File

fun <T> readFileAndProcess(filePath: String, transform: (String) -> T): List<T> =
    File(filePath).useLines { sequence -> sequence.map(transform).toList() }

infix fun Int.mod(other: Int) = Math.floorMod(this, other)

infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

operator fun <K, V> Map<K, V>.invoke(key: K) = this.getValue(key)