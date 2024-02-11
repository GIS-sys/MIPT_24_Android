fun min(a: Long, b: Long): Long {
    return if (a < b) a else b
}

fun max(a: Long, b: Long): Long {
    return -min(-a, -b)
}

fun sign(number: Long): Long {
    if (number == 0L) {
        return 0L
    }
    return if (number < 0L) -1 else 1
}



class Point(var x: Long, var y: Long) {
}

fun orientation(p: Point, q: Point, r: Point): Long {
    return sign((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y))
}



class Segment(var from: Point, var to: Point) {
    fun doesIntersect(other: Segment): Boolean {
        var o1: Long = orientation(this.from, this.to, other.from)
        var o2: Long = orientation(this.from, this.to, other.to)
        var o3: Long = orientation(other.from, other.to, this.from)
        var o4: Long = orientation(other.from, other.to, this.to)
        // General case
        if (o1 != o2 && o3 != o4) {
            return true
        }
        // Special Cases
        return (o1 == 0L && this.isInBounds(other.from))
               || (o2 == 0L && this.isInBounds(other.to))
               || (o3 == 0L && other.isInBounds(this.from))
               || (o4 == 0L && other.isInBounds(this.to))
    }

    fun isInBounds(p: Point) : Boolean {
        return p.x >= min(this.from.x, this.to.x)
               && p.x <= max(this.from.x, this.to.x)
               && p.y >= min(this.from.y, this.to.y)
               && p.y <= max(this.from.y, this.to.y)
    }

    companion object {
        fun readFromStdin(): Segment {
            var (x1, y1, x2, y2) = readln().split(" ")
            return Segment(
                Point(x1.toLong(), y1.toLong()),
                Point(x2.toLong(), y2.toLong())
            )
        }
    }
}



fun main(args: Array<String>) {
    var road = Segment.readFromStdin()
    var n = readln().toLong()
    var bridges: Long = 0
    for (i in 1..n) {
        var river = Segment.readFromStdin()
        if (road.doesIntersect(river)) {
            bridges += 1
        }
    }
    println(bridges)
}

