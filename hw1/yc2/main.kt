fun fibMod(n: Long, m: Long): Long {
    var cache: List<Long> = listOf(0, 1);
    var fibPrev: Long = 1;
    var fibCur: Long = 1;
    while (fibPrev != cache[0] || fibCur != cache[1]) {
        cache += fibCur;
        var fibTemp = fibPrev;
        fibPrev = fibCur;
        fibCur = (fibTemp + fibCur) % m;
    }
    return cache[(n % (cache.size - 1)).toInt()];
}

fun main(args: Array<String>) {
    var (n, m) = readln().split(' ')
    println(fibMod(n.toLong(), m.toLong()))
}

