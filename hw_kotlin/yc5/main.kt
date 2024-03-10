import java.io.File

data class ValueWithMax(val value: Long, val max: Long)

class StackWithMaximum {
    private val listWithMax: MutableList<ValueWithMax> = mutableListOf()

    fun push(value: Long) {
        val newMax = if (this.empty() || value > max()) value else max()
        listWithMax += ValueWithMax(value, newMax)
    }

    fun empty(): Boolean {
        return listWithMax.size == 0
    }

    fun pop() {
        listWithMax.removeLast()
    }

    fun max(): Long {
        return listWithMax.last().max
    }
}

fun main(args: Array<String>) {
    var stack = StackWithMaximum()
    var n: Long = 0
    File("output.txt").printWriter().use { out -> 
        File("input.txt").forEachLine {
            if (n == 0L) {
                n = it.toLong()
            } else {
                if (it.startsWith("push")) {
                    var value = it.substring(5).toLong()
                    stack.push(value)
                } else if (it.startsWith("pop")) {
                    stack.pop()
                } else if (it.startsWith("max")) {
                    out.println(stack.max())
                } else {
                    TODO("Unexpected command")
                }
            }
        }
    }
}

