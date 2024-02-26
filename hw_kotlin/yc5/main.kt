import java.io.File

class StackWithMaximum {
    var listMain: MutableList<Long> = mutableListOf()
    var listMax: MutableList<Long> = mutableListOf()

    fun push(value: Long) {
        listMax += if (this.empty() || value > max()) value else max()
        listMain += value
    }

    fun empty(): Boolean {
        return listMain.size == 0
    }

    fun pop() {
        listMain.removeLast()
        listMax.removeLast()
    }

    fun max(): Long {
        return listMax.last()
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

