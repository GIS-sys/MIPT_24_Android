fun gcd(aArg: Int, bArg: Int): Int {
    var a = aArg;
    var b = bArg;
	while (b != 0) {
        var c = a;
        a = b;
        b = c % b;
    }
    return a;
}

fun main(args: Array<String>) {
	var (a, b) = readln().split(' ')
    println(gcd(a.toInt(), b.toInt()))
}

