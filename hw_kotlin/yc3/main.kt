class Node(var key: Long) {
    private var childLeft: Node? = null
    private var childRight: Node? = null

    fun add(newValue: Long) {
        if (this.key <= newValue) {
            if (childRight != null) {
                childRight?.add(newValue)
            } else {
                childRight = Node(newValue)
            }
        } else {
            if (childLeft != null) {
                childLeft?.add(newValue)
            } else {
                childLeft = Node(newValue)
            }
        }
    }

    fun preOrder(callback: (value: Long) -> Unit) {
        callback(this.key)
        if (childLeft != null) {
            childLeft?.preOrder(callback)
        }
        if (childRight != null) {
            childRight?.preOrder(callback)
        }
    }
}

fun main(args: Array<String>) {
    var n = readln().toInt()
    var rootNumber = readln().toLong()
    var tree = Node(rootNumber)
    repeat(n - 1) {
        var number = readln().toLong()
        tree.add(number)
    }
    tree.preOrder({value -> print(value.toString() + " ")})
}

