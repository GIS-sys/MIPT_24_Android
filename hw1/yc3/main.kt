class Node(var key: Long) {
    var childLeft: Node? = null
    var childRight: Node? = null

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

    fun preOrder() {
        print(this.key.toString() + " ")
        if (childLeft != null) {
            childLeft?.preOrder()
        }
        if (childRight != null) {
            childRight?.preOrder()
        }
    }
}

fun main(args: Array<String>) {
    var n = readln().toLong()
    var rootNumber = readln().toLong()
    var tree = Node(rootNumber)
    for (i in 2..n) {
        var number = readln().toLong()
        tree.add(number)
    }
    tree.preOrder()
}

