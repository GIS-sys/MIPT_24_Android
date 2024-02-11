/*class Node(value: Long) {
    var childLeft = null
    var childRight = null
}*/

fun main(args: Array<String>) {
    var n = readln().toLong()
    var tree: Set<Long> = setOf()
    for (i in 1..n) {
        var number = readln().toLong()
        tree += number
    }
    for (number in tree) {
        print(number.toString())
    }
}

