class Matrix2D(val a11: Long, val a12: Long, val a21: Long, val a22: Long) {
    // a11 a12
    // a21 a22

    fun copy(): Matrix2D {
        return Matrix2D(a11, a12, a21, a22)
    }

    fun mulMat(other: Matrix2D, mod: Long): Matrix2D {
        return Matrix2D(
            (a11 * other.a11 + a12 * other.a21) % mod,
            (a11 * other.a12 + a12 * other.a22) % mod,
            (a21 * other.a11 + a22 * other.a21) % mod,
            (a21 * other.a12 + a22 * other.a22) % mod
        )
    }

    fun square(mod: Long): Matrix2D {
        return this.mulMat(this, mod)
    }
}

fun fibMod(n: Long, m: Long): Long {
    // matrix 0 1
    //        1 1
    // M * (f1, f2)^T = (f2, f3)^T
    var fibMatPower = Matrix2D(0, 1, 1, 1)
    var curMat = Matrix2D(1, 0, 0, 1)
    var binN = n - 1
    while (binN > 0) {
        if (binN % 2 == 1L) {
            curMat = curMat.mulMat(fibMatPower, m)
        }
        fibMatPower = fibMatPower.square(m)
        binN /= 2
    }
    return (curMat.a11 + curMat.a12) % m;
}

fun stupidFibMod(n: Long, m: Long): Long {
    var fibPrev = 0L
    var fibCur = 1L
    for (i in 1..n) {
        var fibTemp = fibPrev
        fibPrev = fibCur
        fibCur = (fibTemp + fibCur) % m
    }
    return fibPrev
}

fun main(args: Array<String>) {
    // TEST
    /*if (true) {
        for (n in 1L..10L) {
            for (m in 2L..10L) {
                if (fibMod(n, m) != stupidFibMod(n, m)) {
                    println(n.toString() + " " + m.toString() + " " + fibMod(n, m).toString() + " " + stupidFibMod(n, m).toString())
                }
            }
        }
    }*/
    var (n, m) = readln().split(' ')
    println(fibMod(n.toLong(), m.toLong()))
}

